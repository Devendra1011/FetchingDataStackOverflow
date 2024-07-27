package com.example.fetchingdatastackoverflow;

import android.app.Application;

import androidx.annotation.UiThread;

import com.example.fetchingdatastackoverflow.common.Constants;
import com.example.fetchingdatastackoverflow.dependencyInjection.CompositionRoot;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionDetailsUseCase;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {


   private CompositionRoot compositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        compositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot(){
        return compositionRoot;
    }
}
