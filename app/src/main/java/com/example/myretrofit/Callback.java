package com.example.myretrofit;

public interface Callback<T> {
    void onFailed(Exception e);

    void onResult(T responseData);
}
