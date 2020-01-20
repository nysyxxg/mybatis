package com.design.pattern.compose.demo02;

import org.apache.ibatis.scripting.xmltags.TextSqlNode;

import java.util.ArrayList;
import java.util.List;

public class MySqlNodeTest {
    public static void main(String[] args) {
        List<MySqlNode> ifSqlNodes = new ArrayList<MySqlNode>();
        MySqlNode sqlNode = new MyChooseSqlNode(ifSqlNodes);

        MyTextSqlNode textSqlNode = new MyTextSqlNode("A");
        MyTextSqlNode textSqlNode2 = new MyTextSqlNode("B");
        MyTextSqlNode textSqlNode3 = new MyTextSqlNode("C");
        ifSqlNodes.add(textSqlNode);
        ifSqlNodes.add(textSqlNode2);
        ifSqlNodes.add(textSqlNode3);

        sqlNode.apply("----haha-----");


    }
}
