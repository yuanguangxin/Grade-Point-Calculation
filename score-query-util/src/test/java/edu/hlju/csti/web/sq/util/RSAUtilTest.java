package edu.hlju.csti.web.sq.util;

import org.junit.Test;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/11
 * 描述:
 */
public class RSAUtilTest {
    @Test
    public void generateKeyPair() throws Exception {
        System.out.println(RSAUtil.generateKeyPair(1024).get("publicKey"));
    }

}