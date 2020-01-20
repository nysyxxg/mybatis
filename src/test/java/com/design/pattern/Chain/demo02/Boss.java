package com.design.pattern.Chain.demo02;

//Boss
public class Boss extends LeaveHandler {
    @Override
    public void handleRequest(int request) {
        System.out.println("Boss已同意");
    }

}