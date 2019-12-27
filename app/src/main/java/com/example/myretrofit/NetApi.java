package com.example.myretrofit;

import io.reactivex.Observable;

public interface NetApi {

    @GET("api/weather/city/101030100")
    Observable<Response<ResponseData>> getData();
}
