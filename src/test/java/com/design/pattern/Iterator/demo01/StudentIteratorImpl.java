package com.design.pattern.Iterator.demo01;

import java.util.List;

public class StudentIteratorImpl implements StudentIterator{
    private List<Student> list;
    private int position = 0;
    private Student currentStudent;

    public StudentIteratorImpl(List<Student> list) {
        this.list = list;
    }

    @Override
    public boolean hashNext() {
        return position < list.size();
    }

    @Override
    public Student next() {
        currentStudent = list.get(position);
        position ++;
        return currentStudent;
    }
}
