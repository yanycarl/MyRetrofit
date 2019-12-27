package com.example.myretrofit;

public class Response<T> {
    T data;

    public Response(T data){
        this.data = data;
    }
}
