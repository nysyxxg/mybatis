package com.design.pattern.Chain.demo02;

//三级主管
public class ThirdLevelSupervisor extends LeaveHandler {
    @Override
    public void handleRequest(int request) {
        if (7<=request&&request<=10){
            System.out.println("三级主管已同意");
        }else {
            if (successor!=null){
                //请请求传递给责任链的下一个处理对象处理
                successor.handleRequest(request);
            }
        }
    }
}