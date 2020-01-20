package com.design.pattern.Iterator.demo02;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.RowBounds;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DefaultCursor<T> implements Cursor<T> {

    private final DefaultCursor<T>.CursorIterator cursorIterator = new DefaultCursor.CursorIterator();
    private boolean iteratorRetrieved;

    public boolean isOpen() {
        return true;
    }

    public boolean isConsumed() {
        return true;
    }

    public int getCurrentIndex() {
        return this.cursorIterator.iteratorIndex;
    }

    public Iterator<T> iterator() {
        if (this.iteratorRetrieved) {
            throw new IllegalStateException("Cannot open more than one iterator on a Cursor");
        } else if (this.isClosed()) {
            throw new IllegalStateException("A Cursor is already closed.");
        } else {
            this.iteratorRetrieved = true;
            return this.cursorIterator;
        }
    }

    public void close() {

    }

    protected T fetchNextUsingRowBound() {
        return null;
    }

    protected T fetchNextObjectFromDatabase() {
        return null;
    }

    private boolean isClosed() {
        return true;
    }

    private int getReadItemsCount() {
        return   1;
    }

    // 游标迭代器 CursorIterator 实现了 java.util.Iterator 迭代器接口，这里的迭代器模式跟 ArrayList 中的迭代器几乎一样
    private class CursorIterator implements Iterator<T> {
        T object;
        int iteratorIndex = -1;

        @Override
        public boolean hasNext() {
            if (object == null) {
                object = fetchNextUsingRowBound();
            }
            return object != null;
        }
        @Override
        public T next() {
            T next = object;

            if (next == null) {
                next = fetchNextUsingRowBound();
            }

            if (next != null) {
                object = null;
                iteratorIndex++;
                return next;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove element from Cursor");
        }
    }

}
