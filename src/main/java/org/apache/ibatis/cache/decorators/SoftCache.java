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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * Soft Reference cache decorator
 * Thanks to Dr. Heinz Kabutz for his guidance here.
 * 软引用缓存,核心是SoftReference
 * 软引用就是如果内存够,GC就不会清理内存,只有当内存不够用了会出现OOM时候,才开始执行GC清理。
 * 如果要看明白这个源码首先要先了解一点垃圾回收,垃圾回收的前提是还有没有别的地方在引用这个对象了。
 * 如果没有别的地方在引用就可以回收了。
 * 本类中为了阻止被回收所以声明了一个变量hardLinksToAvoidGarbageCollection，
 * 也指定了一个将要被回收的垃圾队列queueOfGarbageCollectedEntries 。
 * <p>
 * 这个类的主要内容是当缓存value已经被垃圾回收了，就自动把key也清理。
 * Mybatis 在实际中并没有使用这个类。
 *
 * @author Clinton Begin
 */
public class SoftCache implements Cache {
    //链表用来引用元素，防垃圾回收
//  hard Links To Avoid Garbage Collection
//  硬连接,避免垃圾收集
    private final Deque<Object> hardLinksToAvoidGarbageCollection;
    //被垃圾回收的引用队列
// 垃圾要收集的队列 queue Of Garbage Collected Entries
    private final ReferenceQueue<Object> queueOfGarbageCollectedEntries;
    private final Cache delegate;
    private int numberOfHardLinks;  // 硬连接数量


    public SoftCache(Cache delegate) {
        this.delegate = delegate;
        //默认链表可以存256元素
        this.numberOfHardLinks = 256;
        this.hardLinksToAvoidGarbageCollection = new LinkedList<Object>();
        this.queueOfGarbageCollectedEntries = new ReferenceQueue<Object>();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        removeGarbageCollectedItems();
        return delegate.getSize();
    }


    public void setSize(int size) {
        this.numberOfHardLinks = size;
    }

    @Override
    public void putObject(Object key, Object value) {
        removeGarbageCollectedItems();    //1. 清除已经被垃圾回收的key
        //putObject存了一个SoftReference，这样value没用时会自动垃圾回收
        //2. 注意看SoftEntry(),声明一个SoftEnty对象,指定垃圾回收后要进入的队列
        //3. 当SoftEntry中数据要被清理,会添加到类中声明的垃圾要收集的队列中
        delegate.putObject(key, new SoftEntry(key, value, queueOfGarbageCollectedEntries));
    }

    @Override
    public Object getObject(Object key) {
        Object result = null;
        @SuppressWarnings("unchecked") // assumed delegate cache is totally managed by this cache
                SoftReference<Object> softReference = (SoftReference<Object>) delegate.getObject(key);
        if (softReference != null) {
            //核心调用SoftReference.get取得元素
            result = softReference.get();
            if (result == null) {
                delegate.removeObject(key);          //1. 如果数据已经没有了,就清理这个key
            } else {
                // See #586 (and #335) modifications need more than a read lock
                synchronized (hardLinksToAvoidGarbageCollection) { // 保证线程安全
                    //存入经常访问的键值到链表(最多256元素),防止垃圾回收
                    //2. 如果key存在,读取时候加一个锁操作,并将缓存值添加到硬连接集合中,避免垃圾回收
                    hardLinksToAvoidGarbageCollection.addFirst(result);
                    //3. 构造中指定硬链接最大256,所以如果已经有256个key的时候回开始删除最先添加的key
                    if (hardLinksToAvoidGarbageCollection.size() > numberOfHardLinks) {
                        hardLinksToAvoidGarbageCollection.removeLast();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        removeGarbageCollectedItems();
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        //执行三清
        synchronized (hardLinksToAvoidGarbageCollection) {
            hardLinksToAvoidGarbageCollection.clear();       //1.清除硬链接队列
        }
        removeGarbageCollectedItems();    //2. 清除垃圾队列
        delegate.clear();    //2. 清除垃圾队列
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    private void removeGarbageCollectedItems() {
        SoftEntry sv;
        //查看被垃圾回收的引用队列,然后调用removeObject移除他们
        while ((sv = (SoftEntry) queueOfGarbageCollectedEntries.poll()) != null) {
            delegate.removeObject(sv.key);       //清除value已经gc准备回收了,就就将key也清理掉
        }
    }

    private static class SoftEntry extends SoftReference<Object> {  // 继承了软引用对象
        private final Object key;

        SoftEntry(Object key, Object value, ReferenceQueue<Object> garbageCollectionQueue) {
            super(value, garbageCollectionQueue);
            this.key = key;
        }
    }

}
