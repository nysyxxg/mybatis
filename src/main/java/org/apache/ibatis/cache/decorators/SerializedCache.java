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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.io.Resources;

/**
 * @author Clinton Begin
 */

/**
 * 序列化缓存
 * 用途是先将对象序列化成2进制，再缓存,好处是将对象压缩了，省内存， 坏处是速度慢了
 * 从名字上看就是支持序列化的缓存,那么我们就要问了，为啥要支持序列化?
 * 为啥要支持序列化?
 * 因为如果多个用户同时共享一个数据对象时，同时都引用这一个数据对象。
 * 如果有用户修改了这个数据对象，那么其他用户拿到的就是已经修改过的对象，这样就是出现了线程不安全。
 *
 * 如何解决这种问题
 * 加锁当一个线程在操作时候,其他线程不允许操作
 * 新生成一个对象,这样多个线程获取到的数据就不是一个对象了。
 */
public class SerializedCache implements Cache {

    private Cache delegate;

    public SerializedCache(Cache delegate) {
        this.delegate = delegate;
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
    public void putObject(Object key, Object object) { // putObject 将对象序列化成byte[]
        if (object == null || object instanceof Serializable) {
            //先序列化，再委托被包装者putObject
            delegate.putObject(key, serialize((Serializable) object));
        } else {
            throw new CacheException("SharedCache failed to make a copy of a non-serializable object: " + object);
        }
    }

    @Override
    public Object getObject(Object key) {
        //先委托被包装者getObject,再反序列化
        Object object = delegate.getObject(key);      //1. 获取时候将byte[]反序列化成对象
        return object == null ? null : deserialize((byte[]) object);
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

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    private byte[] serialize(Serializable value) {
        try {
            //序列化核心就是ByteArrayOutputStream
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.flush();
            oos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new CacheException("Error serializing object.  Cause: " + e, e);
        }
    }

    private Serializable deserialize(byte[] value) {
        Serializable result;
        try {
            //反序列化核心就是ByteArrayInputStream
            ByteArrayInputStream bis = new ByteArrayInputStream(value);
            ObjectInputStream ois = new CustomObjectInputStream(bis);
            result = (Serializable) ois.readObject();
            ois.close();
        } catch (Exception e) {
            throw new CacheException("Error deserializing object.  Cause: " + e, e);
        }
        return result;
    }
    /**
     * 这种就类似于深拷贝,因为简单的浅拷贝会出现线程安全问题,而这种办法,因为字节在被反序列化时，
     * 会在创建一个新的对象，这个新的对象的数据和原来对象的数据一模一样。所以说跟深拷贝一样。
     */
    //这个Custom不明白何意
    public static class CustomObjectInputStream extends ObjectInputStream {

        public CustomObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            return Resources.classForName(desc.getName());
        }

    }

}
