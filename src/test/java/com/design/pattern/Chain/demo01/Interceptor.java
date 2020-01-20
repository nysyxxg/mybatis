package com.design.pattern.Chain.demo01;

import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * 拦截器
 *
 */
public interface Interceptor {

  //拦截
  Object intercept(Invocation invocation) throws Throwable;

  //插入
  Object plugin(Object target);

  //设置属性
  void setProperties(Properties properties);

}
