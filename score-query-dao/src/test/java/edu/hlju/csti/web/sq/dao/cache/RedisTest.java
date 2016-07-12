package edu.hlju.csti.web.sq.dao.cache;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/12
 * 描述:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dao.xml")
public class RedisTest {
    @Autowired
    private Redis redis;

    @org.junit.Test
    public void checkKeyExist() throws Exception {
        assertFalse(redis.checkKeyExist("aaa"));
    }

    @org.junit.Test
    public void deleteKey() throws Exception {

    }

    @org.junit.Test
    public void set() throws Exception {

    }

    @org.junit.Test
    public void get() throws Exception {

    }

}