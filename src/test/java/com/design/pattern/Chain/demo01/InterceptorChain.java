package com.design.pattern.Chain.demo01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//插件责任链
//           Mybatis中可以添加插件，比如PageHelper，多个插件对对象的包装采用的动态代理，而且是层层代理。
public class InterceptorChain {

    private final List<Interceptor> interceptors = new ArrayList<>();

    //生成代理对象
    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    //添加拦截器也就是插件，可以添加很多个，利用层层代理生成的对象，方法调用的时候就是责任链模式，层层处理
    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(interceptors);
    }

}