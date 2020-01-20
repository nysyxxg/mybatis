package com.design.pattern.Chain.demo02;

//一级主管
public class FirstLevelSupervisor extends LeaveHandler {
    @Override
    public void handleRequest(int request) {
        if (request<=3){
            System.out.println("一级主管已同意");
        }else {
            if (successor!=null){
                //请请求传递给责任链的下一个处理对象处理
                successor.handleRequest(request);
            }
        }
    }
}