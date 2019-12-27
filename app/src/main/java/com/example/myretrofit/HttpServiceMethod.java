package com.example.myretrofit;

import io.reactivex.Observable;

public class HttpServiceMethod<ResponseT> extends ServiceMethod<ResponseT> {

    CallAdapter<ResponseT> callAdapter;
    RequestFactory requestFactory;

    public HttpServiceMethod(RequestFactory request, CallAdapter callAdapter) {
        this.callAdapter = callAdapter;
        this.requestFactory = request;
    }

    public static HttpServiceMethod
    parseAnnotations(RequestFactory request) {

        CallAdapter callAdapter = new CallAdapter<>(request);
        return new HttpServiceMethod<>(request, callAdapter);
    }

    @Override
    public Observable<Response<ResponseT>> invoke(Object[] args) {
        return adapt(this.callAdapter.mCall, args);
    }

    private Observable<Response<ResponseT>> adapt(Call call, Object[] args) {
        return callAdapter.adapt(call);
    }

    public static class CallAdapter<ResponseT> {
        private Call mCall;

        public CallAdapter(RequestFactory requestFactory){
            HTTPClient client = new HTTPClient.Builder().build();

            Url url = new Url.Builder()
                    .setScheme("http")
                    .setHost(requestFactory.mBuilder.baseUrl)
                    .setPath(requestFactory.mBuilder.relativeUrl)
                    .build();

            final Request request = new Request.Builder()
                    .setMethod("GET")
                    .setUrl(url)
                    .build();
            mCall = client.newCall(request);
        }

        public Observable<Response<ResponseT>> adapt(Call call) {
            Observable<Response<ResponseT>> responseObservable = new CallEnqueueObservable<>(call);
            return responseObservable;
        }
    }
}
