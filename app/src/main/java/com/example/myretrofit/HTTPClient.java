package com.example.myretrofit;

public class HTTPClient {

    public Dispatcher dispatcher;

    public HTTPClient(Builder builder){
        dispatcher = new Dispatcher();
    }

    public Call newCall(Request request){
        return new Call(this, request);
    }

    public static class Builder {

        private Request request;

        public Builder(){

        }

        public HTTPClient build(){
            return new HTTPClient(this);
        }
    }
}
