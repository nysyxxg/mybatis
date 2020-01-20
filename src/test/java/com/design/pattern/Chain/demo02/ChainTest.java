package com.design.pattern.Chain.demo02;

public class ChainTest {
    //测试
    public static void main(String[] args) {
        LeaveHandler firstLevelSupervisor = new FirstLevelSupervisor();
        LeaveHandler secondLevelSupervisor = new SecondLevelSupervisor();
        LeaveHandler thirdLevelSupervisor = new ThirdLevelSupervisor();
        LeaveHandler boss = new Boss();
        //形成责任链
        firstLevelSupervisor.setSuccessor(secondLevelSupervisor);
        secondLevelSupervisor.setSuccessor(thirdLevelSupervisor);
        thirdLevelSupervisor.setSuccessor(boss);

        //向一级主管提交7天的假期申请
        firstLevelSupervisor.handleRequest(7);
    }

}
