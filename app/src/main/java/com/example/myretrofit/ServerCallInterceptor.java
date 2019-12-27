package com.example.myretrofit;

public class ServerCallInterceptor implements IInterceptor {
    @Override
    public ResponseData intercept(RealInterceptorChain chain) {
        if(chain.codeC != null){
            chain.codeC.writeContent(chain.request.getPackage());
            ResponseData responseData = chain.codeC.readContent();
            return responseData;
        }
        return null;
    }
}
