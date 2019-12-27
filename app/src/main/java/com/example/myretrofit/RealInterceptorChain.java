package com.example.myretrofit;

import java.util.List;

public class RealInterceptorChain {
    private List<IInterceptor> interceptors;
    int index;
    public Request request;
    public CodeC codeC;


    public RealInterceptorChain(Request request, int index, List<IInterceptor> interceptors,
                                CodeC codeC){
        this.index = index;
        this.interceptors = interceptors;
        this.request = request;
        this.codeC = codeC;
    }

    public ResponseData proceed(Request request, CodeC codeC){
        this.request = request;
        RealInterceptorChain next = new RealInterceptorChain(request, index+1, interceptors, codeC);

        if(index < interceptors.size()){
            IInterceptor interceptor = interceptors.get(index);
            return interceptor.intercept(next);
        }
        else return null;
    }
}
