package com.example.myretrofit;

import io.reactivex.Observable;

public interface NetApi {

    @GET("/api/weather/city/{city_code}")
    Observable<Response<ResponseData>> getData(@Path("city_code") String code);
}
