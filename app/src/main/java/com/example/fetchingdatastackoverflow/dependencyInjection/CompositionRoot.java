package com.example.fetchingdatastackoverflow.dependencyInjection;

import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentManager;

import com.example.fetchingdatastackoverflow.common.Constants;
import com.example.fetchingdatastackoverflow.common.DialogManager;
import com.example.fetchingdatastackoverflow.common.DialogManagerFactory;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionDetailsUseCase;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {

    private Retrofit retrofit;

    private StackoverflowApi stackoverflowApi;

    @UiThread
    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }


    @UiThread
    private StackoverflowApi getStackoverflowApi(){
        if (stackoverflowApi == null){
            stackoverflowApi = getRetrofit().create(StackoverflowApi.class);
        }
        return stackoverflowApi;
    }

    @UiThread
    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase(){
        return new FetchQuestionDetailsUseCase(getStackoverflowApi());
    }


    @UiThread
    public FetchQuestionsListUseCase getFetchQuestionsListUseCase(){
        return new FetchQuestionsListUseCase((getStackoverflowApi()));
    }

    public DialogManagerFactory getDialogManagerFactory(){
        return  new DialogManagerFactory();
    }

}
