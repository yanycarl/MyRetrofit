package com.example.myretrofit;

public interface IInterceptor {
    ResponseData intercept(RealInterceptorChain chain);
}
