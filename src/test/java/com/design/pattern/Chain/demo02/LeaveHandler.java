package com.design.pattern.Chain.demo02;

//处理者基类
public abstract class LeaveHandler {

    //持有指向后继者的引用
    protected LeaveHandler successor;

    //设置后继者
    public void setSuccessor(LeaveHandler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(int request);
}


