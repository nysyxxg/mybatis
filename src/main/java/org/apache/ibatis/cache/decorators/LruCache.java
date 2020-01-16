/*
 *    Copyright 2009-2014 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache.decorators;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.impl.PerpetualCache;

/**
 * Lru (first in, first out) cache decorator
 *
 * @author Clinton Begin
 */
/*
 * LRU是Least Recently Used的缩写，即最近最少使用。
 * 最近最少使用缓存
 * 基于 LinkedHashMap 覆盖其 removeEldestEntry 方法实现。
 * LruCache装饰器: 删除最近使用最少的缓存， 通过内部的LinkedHashMap实现。
 *
 * 首先我们看如何实现 LRU 策略。
 * 它其实就是利用 LinkedHashMap来实现 LRU 策略, JDK 提供的 LinkedHashMap天然就支持 LRU 策略。
 * LinkedHashMap 有一个特点如果开启LRU策略后,每次获取到数据后,都会把数据放到最后一个节点，这样第一个节点肯定是最近最少用的元素。
 */
public class LruCache implements Cache {

    private final Cache delegate;
    //额外用了一个map才做lru，但是委托的Cache里面其实也是一个map，这样等于用2倍的内存实现lru功能
    private Map<Object, Object> keyMap;
    private Object eldestKey;

    public LruCache(Cache delegate) {
        this.delegate = delegate;
        setSize(1024);
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    public void setSize(final int size) {
        // boolean accessOrder 最后一个参数 true ，代表 是否开始LRU策略  ， 开启就往后面放
        keyMap = new LinkedHashMap<Object, Object>(size, .75F, true) {
            private static final long serialVersionUID = 4267176411845948333L;

            //核心就是覆盖 LinkedHashMap.removeEldestEntry方法,
            //返回true或false告诉 LinkedHashMap要不要删除此最老键值
            //LinkedHashMap内部其实就是每次访问或者插入一个元素都会把元素放到链表末尾，
            //这样不经常访问的键值肯定就在链表开头啦
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                boolean tooBig = size() > size;
                if (tooBig) {
                    //这里没辙了，把eldestKey存入实例变量
                    eldestKey = eldest.getKey();
                }
                return tooBig;
            }
        };
    }

    @Override
    public void putObject(Object key, Object value) {
        System.out.println("putObject-->" + LruCache.class.getName());
        delegate.putObject(key, value);//  每次put数据的时候，就会先去清理一次，删除最近最少使用的
        //增加新纪录后，判断是否要将最老元素移除
        cycleKeyList(key);
    }

    @Override
    public Object getObject(Object key) {
        System.out.println("getObject-->" + LruCache.class.getName());
        //get的时候调用一下LinkedHashMap.get，让经常访问的值移动到链表末尾
        keyMap.get(key); //touch
        return delegate.getObject(key);
    }

    @Override
    public Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        delegate.clear();
        keyMap.clear();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    private void cycleKeyList(Object key) {
        keyMap.put(key, key);
        //keyMap是linkedhashmap，最老的记录已经被移除了，然后这里我们还需要移除被委托的那个cache的记录
        if (eldestKey != null) {
            delegate.removeObject(eldestKey);
            eldestKey = null;
        }
    }

}
