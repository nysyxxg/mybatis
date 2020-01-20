package com.design.pattern.compose.demo01;

import java.util.ArrayList;

/**
 * 组合模式的应用
 */
public class RootNode implements NodeComponent {
    //首先来一个存储的集合
    private ArrayList<NodeComponent> list = new ArrayList<NodeComponent>();

    @Override
    public void addComposite(NodeComponent c) {
        list.add(c);
    }

    @Override
    public void removeComposite(NodeComponent c) {
        list.remove(c);
    }

    @Override
    public NodeComponent getComposite(int c) {
        NodeComponent c1 = list.get(c);
        return c1;
    }

    @Override
    public void operation() {
        for (Object obj : list) {
            ((NodeComponent) obj).operation();
        }
    }


}