package edu.hlju.csti.web.sq.service;

import edu.hlju.csti.web.sq.dao.mapper.UserKeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/12
 * 描述:
 */
@Service
public class LoginService {
    @Autowired
    private UserKeyMapper userKeyMapper;

    public Map<String, String> requestPublicKey(String schoolNum) {
        Map<String, String> map = new HashMap<>();
        return map;
    }
}
