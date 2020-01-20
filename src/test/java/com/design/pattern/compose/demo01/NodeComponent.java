package com.design.pattern.compose.demo01;

public interface NodeComponent {

    //这个是容器类的抽象类，定义好行为，定义创建移除子容器的方法抽象的。
    public   void addComposite(NodeComponent c); //添加成员

    public   void removeComposite(NodeComponent c);//移除成员

    public   NodeComponent getComposite(int i);//获取子容器

    public   void operation();//业务方法
}

