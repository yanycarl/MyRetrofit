package com.example.myretrofit;

import android.util.Log;

import java.lang.reflect.Method;

public class ParseAnnotation {

    public static void parseGet(){
        Class clazz;
        {
            try {
                clazz = Class.forName("com.example.myretrofit.NetApi");
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method: methods){
                    GET get = method.getDeclaredAnnotation(GET.class);
                    assert get!=null;
                    String value = get.value();
                    Log.d("yanyao", value);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
