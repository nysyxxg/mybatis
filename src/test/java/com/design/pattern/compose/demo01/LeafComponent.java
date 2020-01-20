package com.design.pattern.compose.demo01;

public class LeafComponent implements NodeComponent {
    private  String name;
    public LeafComponent(String name){
        this.name = name;
    }
    @Override
    public void addComposite(NodeComponent c) {
        System.out.println("..........添加组件......");
    }

    @Override
    public void removeComposite(NodeComponent c) {
        System.out.println("..........删除组件......");
    }

    @Override
    public NodeComponent getComposite(int i) {
        System.out.println(".........获取组件........");
        return null;
    }

    @Override
    public void operation() {
        System.out.println(name + "   业务方法---->操作组件......");
    }
}