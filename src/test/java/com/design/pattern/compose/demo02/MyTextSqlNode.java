package com.design.pattern.compose.demo02;

import org.apache.ibatis.scripting.xmltags.DynamicContext;

import java.util.regex.Pattern;

public class MyTextSqlNode implements  MySqlNode{

    private String text;

    public MyTextSqlNode(String text) {
       this.text = text;
    }

    @Override
    public void apply(String context) {
        System.out.println(text+"-------->" +  context);
    }

}
