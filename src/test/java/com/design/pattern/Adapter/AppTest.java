package com.design.pattern.Adapter;

public class AppTest {

    public static void main(String[] args) {
        String className =  AppTest.class.getName();
        MyLog myLog = new Log4jImpl(className);
        myLog.debug("-------------");
    }
}