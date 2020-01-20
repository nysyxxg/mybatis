package com.design.pattern.Chain.demo02;

//二级主管
public class SecondLevelSupervisor extends LeaveHandler{
    @Override
    public void handleRequest(int request) {
        if (4<=request&&request<=6){
            System.out.println("二级主管已同意");
        }else {
            if (successor!=null){
                //请请求传递给责任链的下一个处理对象处理
                successor.handleRequest(request);
            }
        }
    }
}