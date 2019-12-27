package com.example.myretrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@SuppressWarnings("unchecked")
public class Retrofit {
    public <T>T create(Class<T> service){
        //检测接口是否符合要求
        Utils.validateServiceInterface(service);

        //创建代理的Handler
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    //判断如果是在Object对象里声明的函数，直接调用。
                    if (method.getDeclaringClass() == Object.class) {
                        return method.invoke(this, args);
                    }

                    //TODO：在这里再判断有没有平台定义的函数

                    //按照ServiceMethod处理
                    return loadServiceMethod(method).invoke(null);
                }
        });
    }

    private ServiceMethod<?> loadServiceMethod(Method method){

        //TODO:在这里使用缓存查找之前的ServiceMethod
        ServiceMethod<?> result = ServiceMethod.parseAnnotations(method);
        return result;
    }
}
