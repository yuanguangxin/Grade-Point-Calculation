package edu.hlju.csti.web.sq.controller;

import edu.hlju.csti.web.sq.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/11
 * 描述:
 */
@Controller
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    private Map<String, String> keyMap;

    public TestController() {
        try {
            keyMap = RSAUtil.generateKeyPair(1024);
            logger.info("键:{}", keyMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/enctype", method = RequestMethod.GET)
    public ModelAndView enctype(HttpServletResponse response) throws UnsupportedEncodingException {
        String pubKey = URLEncoder.encode(keyMap.get("publicKey"), "UTF-8");
        logger.info("公钥为:{}", pubKey);
        response.addCookie(new Cookie("publicKey", pubKey));
        return new ModelAndView("testRsa", "publicKey", pubKey);
    }

    @RequestMapping(value = "/enctype", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String result(String enctype) {
        String priKey = keyMap.get("privateKey");
        try {
            logger.info("解密前:{}", enctype);
            String res = RSAUtil.decrypt(enctype, priKey);
            logger.info("私钥为:{}", priKey);
            logger.info("解密后:{}", res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
