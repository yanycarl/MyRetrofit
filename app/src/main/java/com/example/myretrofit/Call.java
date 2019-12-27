package com.example.myretrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Call {

    private HTTPClient client;
    private Request request;

    public Call(HTTPClient httpClient, Request request){
        this.client = httpClient;
        this.request = request;
    }

    public class AsyncCall implements Runnable{

        private Callback callback;

        public AsyncCall(Callback callback){
            this.callback = callback;
        }

        public void executeOn(ExecutorService executorService){
            try{
                executorService.execute(this);
                client.dispatcher.finished(this);
            }catch (Exception e) {
                callback.onFailed(e);
                client.dispatcher.finished(this);
            }
        }

        @Override
        public void run() {
            ResponseData response = getResponseWithInterceptorChain();
            callback.onResult(response);
        }
    }

    public ResponseData execute(){
        return getResponseWithInterceptorChain();
    }

    private ResponseData getResponseWithInterceptorChain(){
        List<IInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new DnsIntercepter());
        interceptors.add(new ConnectInterceptor());
        interceptors.add(new ServerCallInterceptor());
        RealInterceptorChain chain =  new RealInterceptorChain(request, 0,  interceptors, null);
        return chain.proceed(request, null);
    }

    public void enqueue(Callback callback){
        client.dispatcher.enqueue(new AsyncCall(callback));
    }
}
