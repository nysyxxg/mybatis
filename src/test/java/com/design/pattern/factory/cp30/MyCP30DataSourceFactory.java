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
package com.design.pattern.factory.cp30;

import com.design.pattern.factory.MyDataSourceFactory;
import com.design.pattern.factory.dbcp.DBCPDataSource;

import javax.sql.DataSource;

/**
 * @author Clinton Begin
 */

/**
 * JNDI数据源工厂
 * 这个数据源的实现是为了使用如 Spring 或应用服务器这类的容器, 容器可以集 中或在外部配置数据源,然后放置一个 JNDI 上下文的引用。
 */
public class MyCP30DataSourceFactory implements MyDataSourceFactory {

  private DataSource dataSource;

  public MyCP30DataSourceFactory(){
    System.out.println(".......MyCP30DataSourceFactory........初始化了.............");
    this.dataSource = new Cp30DataSource();

  }

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

}
