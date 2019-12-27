package com.example.myretrofit;

import android.util.Log;

import io.reactivex.Observable;

public class HttpServiceMethod<ResponseT> extends ServiceMethod<ResponseT> {

    CallAdapter<ResponseT> callAdapter;
    RequestFactory requestFactory;

    public HttpServiceMethod(RequestFactory request, CallAdapter callAdapter) {
        this.callAdapter = callAdapter;
        this.requestFactory = request;
    }

    public static HttpServiceMethod parseAnnotations(RequestFactory request) {
        CallAdapter callAdapter = new CallAdapter<>();
        return new HttpServiceMethod<>(request, callAdapter);
    }

    @Override
    public Observable<Response<ResponseT>> invoke(Object[] args) {
        HTTPClient client = new HTTPClient.Builder().build();

        for(int i=0; i<requestFactory.mBuilder.parameterHandlers.length;i++){
            if(requestFactory.mBuilder.parameterHandlers[i].type.equals("Path")){
                requestFactory.mBuilder.relativeUrl
                        = requestFactory.mBuilder.relativeUrl
                        .replace("{"+requestFactory.mBuilder.parameterHandlers[i].mName+"}",
                                (String)args[i]);
                Log.d("yanyao222", requestFactory.mBuilder.relativeUrl);
            }
        }

        Url url = new Url.Builder()
                .setScheme("http")
                .setHost(requestFactory.mBuilder.baseUrl)
                .setPath(requestFactory.mBuilder.relativeUrl)
                .build();

        final Request request = new Request.Builder()
                .setMethod("GET")
                .setUrl(url)
                .build();
        Call mCall = client.newCall(request);

        return adapt(mCall, args);
    }

    private Observable<Response<ResponseT>> adapt(Call call, Object[] args) {
        return callAdapter.adapt(call);
    }

    public static class CallAdapter<ResponseT> {

        public Observable<Response<ResponseT>> adapt(Call call) {
            Observable<Response<ResponseT>> responseObservable = new CallEnqueueObservable<>(call);
            return responseObservable;
        }
    }
}
