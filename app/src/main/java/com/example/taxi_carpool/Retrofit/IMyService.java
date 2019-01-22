package com.example.taxi_carpool.Retrofit;

import com.example.taxi_carpool.TaxiParty;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IMyService {
    @POST("sign-up")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("phoneNumber") String phonenum,
                                    @Field("password") String password,
                                    @Field("company") String accountCompany,
                                    @Field("account") String account);


    @POST("log-in")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("phoneNumber") String phonenum,
                                 @Field("password") String password);

    @POST("new-party")
    @FormUrlEncoded
    Observable<String> makeNewParty(@Field("title") String roomTitle,
                                    @Field("departure") String startPoint,
                                    @Field("destination") String endPoint,
                                    @Field("date") String date,
                                    @Field("numLeft") String peopleWant,
                                    @Field("explanation") String extra);

    @POST("send-message")
    @FormUrlEncoded
    Observable<String> sendMessage(@Field("currentTaxiParty") String currentTaxiParty);


    @PUT("enter-party")
    @FormUrlEncoded
    Observable<String> enterParty(@Field("phoneNumber") Integer phonenum,
                                 @Field("currentTaxiParty") String partyId);

    @POST("current-taxi-party")
    @FormUrlEncoded
    Observable<String> loadMyParty(@Field("phoneNumber") Integer phonenum);
}
