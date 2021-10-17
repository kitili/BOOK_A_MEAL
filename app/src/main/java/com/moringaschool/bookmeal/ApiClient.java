package com.moringaschool.bookmeal;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.*;

public class ApiClient {
    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://bookameal.herokuapp.com/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }
    public static UserService getService(){
        UserService userService=getRetrofit().create(UserService.class);
        return userService;
    }
}
