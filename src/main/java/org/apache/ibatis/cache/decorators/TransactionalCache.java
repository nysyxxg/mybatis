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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * The 2nd level cache transactional buffer.
 * <p>
 * This class holds all cache entries that are to be added to the 2nd level cache during a Session.
 * Entries are sent to the cache when commit is called or discarded if the Session is rolled back.
 * Blocking cache support has been added. Therefore any get() that returns a cache miss
 * will be followed by a put() so any lock associated with the key can be released.
 *
 * @author Clinton Begin
 * @author Eduardo Macarron
 */

/**
 * 事务缓存
 * 一次性存入多个缓存，移除多个缓存
 *  从名字上看就应该能隐隐感觉到跟事务有关,但是这个事务呢又不是数据库的那个事务。
 *  只是类似而已是, 即通过 java 代码来实现了一个暂存区域,如果事务成功就添加缓存，事务失败就回滚掉或者说就把暂存区的信息删除,
 *  不进入真正的缓存里面。
 *  这个类是比较重要的一个类,因为所谓的二级缓存就是指这个类。既然说了🎧缓存就顺便提一下一级缓存。
 *  但是说一级缓存就设计到 Mybatis架构里面一个 Executor 执行器
 */
public class TransactionalCache implements Cache {
    // 真正的缓存
    private Cache delegate;
    //commit时要不要清缓存,    // 是否清理已经提交的事务
    private boolean clearOnCommit;
    //commit时要添加的元素,   // 可以理解为暂存区
    private Map<Object, Object> entriesToAddOnCommit;
    // 缓存中没有的key
    private Set<Object> entriesMissedInCache;

    public TransactionalCache(Cache delegate) {
        this.delegate = delegate;
        //默认commit时不清缓存
        this.clearOnCommit = false;
        this.entriesToAddOnCommit = new HashMap<Object, Object>();
        this.entriesMissedInCache = new HashSet<Object>();
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
    public Object getObject(Object key) {
        // issue #116
        Object object = delegate.getObject(key);      // 先从缓存中拿数据
        if (object == null) {
            entriesMissedInCache.add(key);         // 如果没有, 添加到set集合中
        }
        // issue #146
        if (clearOnCommit) {      // 返回数据库的数据。
            return null;
        } else {
            return object;
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    @Override
    public void putObject(Object key, Object object) {
        entriesToAddOnCommit.put(key, object);
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {
        clearOnCommit = true;
        entriesToAddOnCommit.clear();
    }

    //多了commit方法，提供事务功能
    public void commit() {
        if (clearOnCommit) {
            delegate.clear();
        }
        flushPendingEntries();
        reset();
    }

    public void rollback() {
        unlockMissedEntries();
        reset();
    }

    private void reset() {
        clearOnCommit = false;       //1. 是否清除提交
        entriesToAddOnCommit.clear(); //2. 暂存区清理,代表这个事务从头开始做了，之前的清理掉
        entriesMissedInCache.clear();  //3. 同上
    }

    /**
     *    * 将暂存区的数据提交到缓存中
     */
    private void flushPendingEntries() {
        for (Map.Entry<Object, Object> entry : entriesToAddOnCommit.entrySet()) {
            delegate.putObject(entry.getKey(), entry.getValue()); // * 将暂存区的数据提交到缓存中
        }
        //如果缓存中不包含这个key,就将key对应的value设置为默认值null
        for (Object entry : entriesMissedInCache) {
            if (!entriesToAddOnCommit.containsKey(entry)) {
                delegate.putObject(entry, null);
            }
        }
    }
    // 移除缺失的key,就是这个缓存中没有的key都移除掉
    private void unlockMissedEntries() {
        for (Object entry : entriesMissedInCache) {
            delegate.putObject(entry, null);
        }
    }

}
