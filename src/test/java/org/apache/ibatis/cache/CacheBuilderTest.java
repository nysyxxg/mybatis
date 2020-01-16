package org.apache.ibatis.cache;

import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.mapping.CacheBuilder;
import org.junit.Test;

import java.util.Properties;

/**
 * 构建器和 装饰器模式联合使用
 */
public class CacheBuilderTest {
    String currentNamespace = "";

    @Test
    public void testCacheBuilderTest() {
        Class<? extends Cache> typeClass = PerpetualCache.class;
        Class<? extends Cache> evictionClass = LruCache.class;
        long flushInterval = 100;
        Integer size = 10;
        boolean readWrite = true;
        boolean blocking = false;
        Properties props = new Properties();
//        typeClass = valueOrDefault(typeClass, PerpetualCache.class);
//        evictionClass = valueOrDefault(evictionClass, LruCache.class);

        Cache cache = new CacheBuilderTest().useNewCache(typeClass, evictionClass, flushInterval, size,
                readWrite, blocking, props);
        cache.putObject("key-123", "value-123");
        String value = (String) cache.getObject("key-123");
        System.out.println(value);
    }


    public Cache useNewCache(Class<? extends Cache> typeClass,
                             Class<? extends Cache> evictionClass,
                             Long flushInterval,
                             Integer size,
                             boolean readWrite,
                             boolean blocking,
                             Properties props) {
        //这里面又判断了一下是否为null就用默认值，有点和XMLMapperBuilder.cacheElement逻辑重复了
        typeClass = valueOrDefault(typeClass, PerpetualCache.class);
        evictionClass = valueOrDefault(evictionClass, LruCache.class);
        //调用CacheBuilder构建cache,id=currentNamespace
        Cache cache = new CacheBuilder(currentNamespace)
                .implementation(typeClass)
                .addDecorator(evictionClass)
                .clearInterval(flushInterval)
                .size(size)
                .readWrite(readWrite)
                .blocking(blocking)
                .properties(props)
                .build();
        return cache;
    }

    private <T> T valueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
