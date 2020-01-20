package com.design.pattern.Iterator.demo02;

import java.io.Closeable;

/**
 *  在mybatis3.5.3 版本中：
 *   当查询数据库返回大量的数据项时可以使用游标 Cursor，
 *   利用其中的迭代器可以懒加载数据，避免因为一次性加载所有数据导致内存奔溃，
 *   Mybatis 为 Cursor 接口提供了一个默认实现类 DefaultCursor，代码如下
 * @param <T>
 */
public interface Cursor<T> extends Closeable, Iterable<T> {
    boolean isOpen();
    boolean isConsumed();
    int getCurrentIndex();
}
