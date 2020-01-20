/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.design.pattern.compose.demo02;

import org.apache.ibatis.scripting.xmltags.DynamicContext;

import java.util.List;

/**
 * @author Clinton Begin
 */

/**
 * choose SQL节点
 * 设计模式：组合模式
 */
public class MyChooseSqlNode implements MySqlNode {
    // 组合模式的使用
    private List<MySqlNode> ifSqlNodes;

    public MyChooseSqlNode(List<MySqlNode> ifSqlNode) {
        this.ifSqlNodes = ifSqlNodes;
    }

    @Override
    public void apply(String context) {
        //循环判断if，只要有1个为true了，返回true
        for (MySqlNode sqlNode : ifSqlNodes) {
             sqlNode.apply(context) ;
        }
    }
}
