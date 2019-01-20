package com.example.taxi_carpool.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyService {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("phoneNumber") String phonenum,
                            @Field("password") String password,
                            @Field("company") String accountCompany,
                            @Field("account") String account);


    @POST("log-in")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("phoneNumber") String phonenum,
                            @Field("password") String password);

}
