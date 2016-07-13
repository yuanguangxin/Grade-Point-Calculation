package edu.hlju.csti.web.sq.service;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import edu.hlju.csti.web.sq.dao.mapper.UserKeyMapper;
import edu.hlju.csti.web.sq.enums.StatusCode;
import edu.hlju.csti.web.sq.io.LoginInput;
import edu.hlju.csti.web.sq.io.OutputSimple;
import edu.hlju.csti.web.sq.model.UserKey;
import edu.hlju.csti.web.sq.util.HttpClientUtil;
import edu.hlju.csti.web.sq.util.RSAUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/12
 * 描述:
 */
@Service
public class LoginService {
    private static Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private UserKeyMapper userKeyMapper;
    @Value("#{config['heida.captcha.url']}")
    private String captchaUrl;
    @Value("#{config['heida.login.url']}")
    private String loginUrl;
    @Autowired
    private RedisTemplate<String, Map<String, String>> redisTemplate;

    public OutputSimple requestPublicKey(String schoolNum) throws UnsupportedEncodingException {
        Map<String, String> retVal = new HashMap<>();
        ValueOperations<String, Map<String, String>> valueOperations = redisTemplate.opsForValue();
        Map<String, String> pk = valueOperations.get(schoolNum);
        if (pk != null) {
            retVal.put("sessionKey", Base64.encode((schoolNum + "|" + pk.get("publicKey")).getBytes()));
            logger.info("return by redis:{}", retVal);
            return new OutputSimple(retVal);
        }
        UserKey uk = userKeyMapper.selectBySchoolNum(schoolNum);
        if (uk != null) {
            Map<String, String> map = new HashMap<>();
            map.put("publicKey", uk.getPublicKey());
            map.put("privateKey", uk.getPrivateKey());
            valueOperations.set(schoolNum, map);
            retVal.put("sessionKey", Base64.encode((schoolNum + "|" + uk.getPrivateKey()).getBytes()));
            logger.info("return by mysql:{}", retVal);
            return new OutputSimple(retVal);
        }
        try {
            Map<String, String> map = RSAUtil.generateKeyPair(1024);
            valueOperations.set(schoolNum, map);
            UserKey userKey = new UserKey();
            userKey.setSchoolNum(schoolNum);
            userKey.setPublicKey(map.get("publicKey"));
            userKey.setPrivateKey(map.get("privateKey"));
            userKey.setUpdateDate(new Date());
            userKeyMapper.insertSelective(userKey);
            retVal.put("sessionKey", Base64.encode((schoolNum + "|" + map.get("publicKey")).getBytes()));
            logger.info("return by generate:{}", retVal);
            return new OutputSimple(retVal);
        } catch (Exception e) {
            logger.error("Error:{}", e);
        }
        return new OutputSimple(StatusCode.SERVER_ERROR.getCode(), "服务器异常,请稍后再试");
    }

    public OutputSimple getCaptcha() {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        HttpResponse response = null;
        for (int i = 1; i <= 3; i++) {
            response = httpClientUtil.executeGet(captchaUrl.replaceAll("\\[n\\]", String.valueOf(i)));
            if (response.getStatusLine().getStatusCode() == 200) {
                break;
            } else {
                response = null;
            }
        }
        if (response == null) {
            return new OutputSimple(StatusCode.REQUEST_ERROR.getCode(), "验证码获取失败,服务器无响应");
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                String base64Code = Base64.encode(EntityUtils.toByteArray(entity));
                String contextType = entity.getContentType().getValue();
                Map<String, String> map = new HashMap<>();
                map.put("captcha", "data:" + contextType + ";base64," + base64Code);
                return new OutputSimple(map);
            } catch (IOException e) {
                logger.error("error:{}", e);
            }
        }
        return new OutputSimple(StatusCode.SERVER_ERROR.getCode(), "服务器异常,请稍后再试");
    }


    public OutputSimple executeLogin(LoginInput input) {
        if (input == null) {
            return new OutputSimple(StatusCode.REQUEST_ERROR.getCode(), "参数错误");
        }
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String schoolNum = input.getSchoolNum();
        String privateKey = null;
        ValueOperations<String, Map<String, String>> valueOperations = redisTemplate.opsForValue();
        Map<String, String> map = valueOperations.get(schoolNum);
        if (map != null) {
            privateKey = map.get("privateKey");
        } else {
            UserKey userKey = userKeyMapper.selectBySchoolNum(schoolNum);
            if (userKey != null) {
                privateKey = userKey.getPrivateKey();
            }
        }
        if (privateKey == null) {
            return new OutputSimple(StatusCode.SERVER_ERROR.getCode(), "登录失败");
        }
        logger.info("get input:{}", input);
        try {
            String password = RSAUtil.decrypt(input.getPassword(), privateKey);
            logger.info("解密后密码为:{}", password);
            Map<String, String> params = new HashMap<>();
            params.put("j_username", schoolNum);
            params.put("j_password", password);
            params.put("validateCode", input.getCaptcha());
            HttpResponse response = null;
            for (int i = 1; i < 3; i++) {
                response = httpClientUtil.executePost(
                        loginUrl.replaceAll("\\[n\\]", String.valueOf(i)),
                        params, null, null);
                logger.info("登录请求失败:{}", response);
                if (response != null && response.getStatusLine().getStatusCode() == 200) {
                    break;
                } else {
                    response = null;
                }
            }
            if (response == null) {
                return new OutputSimple(StatusCode.REQUEST_ERROR.getCode(), "登录失败,服务器无响应");
            }
            logger.info("返回数据为:{}", EntityUtils.toString(response.getEntity()));
            return new OutputSimple();
        } catch (Exception e) {
            logger.error("error:{}", e);
        }
        return new OutputSimple(StatusCode.SERVER_ERROR.getCode(), "服务器异常,请稍后再试");
    }
}
