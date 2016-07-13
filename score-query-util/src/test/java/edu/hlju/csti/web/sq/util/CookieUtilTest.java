package edu.hlju.csti.web.sq.util;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * 开发者:timeliar
 * 开发时间:16-7-11
 * 描述:
 */
public class CookieUtilTest {
    @Test
    public void getBody() throws Exception {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        HttpResponse response = httpClientUtil.executeGet("http://o6kxlq2jd.bkt.clouddn.com/ad.jpg");
        System.out.println(response.getEntity().getContentType().getValue());
        System.out.println(new String(Base64.encode(EntityUtils.toByteArray(response.getEntity()))));
    }

}