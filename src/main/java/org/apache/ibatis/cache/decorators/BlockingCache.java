package org.apache.ibatis.cache.decorators;
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;

/**
 * Simple blocking decorator
 * <p>
 * Sipmle and inefficient version of EhCache's BlockingCache decorator.
 * It sets a lock over a cache key when the element is not found in cache.
 * This way, other threads will wait until this element is filled instead of hitting the database.
 *
 * @author Eduardo Macarron
 * 简单阻塞缓存 ：BlockingCache是一个简单和低效的Cache的装饰器
 * BlockingCache装饰器：保证只有一个线程到数据库中查询指定key的数据，
 * 如果该线程在BlockingCache中未查找到数据，就获取key对应的锁，阻塞其他查询这个key的线程，通过其内部ConcurrentHashMap来实现，
 *
 */
public class BlockingCache implements Cache {

    private long timeout;
    //实现Cache接口的缓存对象： 代理存储对象
    private final Cache delegate;
    //对每个key生成一个锁对象
    // 注意这个加lock并不是对get方法加lock,而是对每个要get的key来加lock。
    private final ConcurrentHashMap<Object, ReentrantLock> locks;

    public BlockingCache(Cache delegate) {
        this.delegate = delegate;
        this.locks = new ConcurrentHashMap<Object, ReentrantLock>();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    @Override
    public void putObject(Object key, Object value) {
        try {
            delegate.putObject(key, value);
        } finally {
            //  释放锁。 为什么不加锁? 所以get和put是组合使用的，
            //  当get加锁,如果没有就查询数据库然后put释放锁，然后其他线程就可以直接用缓存数据了。
            releaseLock(key);
        }
    }

    @Override
    public Object getObject(Object key) {
        //1. 当要获取一个key,首先对key进行加锁操作,如果没有锁就加一个锁,有锁就直接锁
        acquireLock(key);
        Object value = delegate.getObject(key);
        if (value != null) { // 如果对象存在缓存中，就释放锁
            releaseLock(key);      //2. 如果缓存命中,就直接解锁
        }
        //3. 当value=null, 就是说没有命中缓存,那么这个key就会被锁住,其他线程进来都要等待
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    private ReentrantLock getLockForKey(Object key) {
        ReentrantLock lock = new ReentrantLock();
        //   如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null
        ReentrantLock previous = locks.putIfAbsent(key, lock);
        //如果key对应的锁存在就返回,没有就创建一个新的
        return previous == null ? lock : previous;
    }

    private void acquireLock(Object key) {
        Lock lock = getLockForKey(key);
        //1. 如果设置超时时间,就可以等待timeout时间(如果超时了报错)
        if (timeout > 0) {
            try {
                boolean acquired = lock.tryLock(timeout, TimeUnit.MILLISECONDS); // 尽量获取锁
                if (!acquired) {
                    throw new CacheException("Couldn't get a lock in " + timeout + " for the key " + key + " at the cache " + delegate.getId());
                }
            } catch (InterruptedException e) {
                throw new CacheException("Got interrupted while trying to acquire lock for key " + key, e);
            }
        } else {
            //2. 如果没有设置,直接就加锁(如果这个锁已经被人用了,那么就一直阻塞这里。等待上一个释放锁)
            lock.lock();
        }
    }

    private void releaseLock(Object key) {
        ReentrantLock lock = locks.get(key);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
