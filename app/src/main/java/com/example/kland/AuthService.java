package com.example.kland;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {

    @FormUrlEncoded
    @POST("api/chart-message/fb-login")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
