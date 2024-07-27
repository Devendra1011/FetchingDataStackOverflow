package com.example.fetchingdatastackoverflow;

import android.app.Application;

import androidx.annotation.UiThread;

import com.example.fetchingdatastackoverflow.common.Constants;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {


    private Retrofit retrofit;

    private StackoverflowApi stackoverflowApi;

    @UiThread
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }


    @UiThread
    public StackoverflowApi getStackoverflowApi(){
        if (stackoverflowApi == null){
            stackoverflowApi = getRetrofit().create(StackoverflowApi.class);
        }
        return stackoverflowApi;
    }
}
