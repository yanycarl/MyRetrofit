package com.example.myretrofit;

public class ConnectInterceptor implements IInterceptor {

    @Override
    public ResponseData intercept(RealInterceptorChain chain) {
        CodeC codeC = new CodeC(chain.request.getIp());
        return chain.proceed(chain.request, codeC);
    }
}
