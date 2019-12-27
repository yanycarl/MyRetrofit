package com.example.myretrofit;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import io.reactivex.Observable;

public abstract class ServiceMethod<T> {

    public static <T> ServiceMethod<T> parseAnnotations(Method method) {

        Type returnType = method.getGenericReturnType();
        if (returnType == void.class) {
            throw new IllegalArgumentException("Service methods cannot return void.");
        }

        RequestFactory requestFactory = RequestFactory.parseAnnotations(method);
        requestFactory.mBuilder.baseUrl = "t.weather.sojson.com";

        return HttpServiceMethod.parseAnnotations(requestFactory);
    }

    abstract public Observable<Response<T>> invoke(Object[] args);
}
