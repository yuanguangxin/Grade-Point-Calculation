package edu.hlju.csti.web.sq.dao.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

/**
 * 开发者:李嘉鼎
 * 开发时间:2016/7/12
 * 描述:
 */
public class LocalCache implements CacheManager {
    @Override
    public Cache getCache(String s) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
}
