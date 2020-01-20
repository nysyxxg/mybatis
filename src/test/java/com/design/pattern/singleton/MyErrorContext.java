package com.design.pattern.singleton;

/**
 * 使用ThreadLocal  实现单例模式
 */
public class MyErrorContext {
    private static final ThreadLocal<MyErrorContext> LOCAL = new ThreadLocal<MyErrorContext>();
    private MyErrorContext(){ // 私有的构造方法
    }

    public  static MyErrorContext  getInstance(){
        // 先从ThreadLocal 获取
        MyErrorContext  myErrorContext =  LOCAL.get();
        if(null == myErrorContext){
            myErrorContext = new MyErrorContext();
            // 放入到 ThreadLocal
            LOCAL.set(myErrorContext);
        }
        return myErrorContext;
    }

}
