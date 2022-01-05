package com.tusk.priest.server.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CacheUtil {

    private static CacheManager manager;

    @Autowired
    public void setManager(CacheManager manager) {
        CacheUtil.manager = manager;
    }

    public static Map<Object, Element> get(String cacheName) {
        Cache cache = cache(cacheName);
        return cache.getAll(cache.getKeys());
    }

    public static Object get(String cacheName, Object key) {
        return cache(cacheName).get(key).getObjectValue();
    }

    public static void put(String cacheName, Object key, Object value, Integer ttl, Integer tti) {
        Element e = new Element(key, value);
        //不设置则使用xml配置
        if (ttl != null)
            e.setTimeToLive(ttl);
        if (tti != null)
            e.setTimeToIdle(tti);
        cache(cacheName).put(e);
    }

    public static void put(String cacheName, Object key, Object value) {
        put(cacheName, key, value, null, null);
    }

    public static boolean remove(String cacheName, Object key) {
        return cache(cacheName).remove(key);
    }

    public static void removeAll(String cacheName) {
        cache(cacheName).removeAll();
    }

    private static Cache cache(String cacheName) {
        net.sf.ehcache.CacheManager cacheManager = ((EhCacheCacheManager) manager).getCacheManager();
        if (!cacheManager.cacheExists(cacheName))
            cacheManager.addCache(cacheName);
        return cacheManager.getCache(cacheName);
    }
}
