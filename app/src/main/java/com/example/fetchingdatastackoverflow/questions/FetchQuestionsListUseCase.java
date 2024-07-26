package com.example.fetchingdatastackoverflow.questions;


import androidx.annotation.Nullable;

import com.example.fetchingdatastackoverflow.Constants;
import com.example.fetchingdatastackoverflow.networking.QuestionsListResponseSchema;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;
import com.example.fetchingdatastackoverflow.questionsList.BaseObservable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionsListUseCase extends BaseObservable<FetchQuestionsListUseCase.Listener> {
    public interface Listener{
        void  onFetchOfQuestionsSucceded(List<Question> questions);
        void onFetchOfQuestionsFailed();


    }

    private final StackoverflowApi stackoverflowApi;


    @Nullable
    Call<QuestionsListResponseSchema> call;


    public FetchQuestionsListUseCase(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        this.stackoverflowApi = retrofit.create(StackoverflowApi.class);

    }

    public void fetchLastActiveQuestionsAndNotify(int numOfQuestions){
        cancelCurrentFetchIfActive();
        call = stackoverflowApi.lastActiveQuestions(numOfQuestions);
        call.enqueue(new Callback<QuestionsListResponseSchema>() {
            @Override
            public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                if (response.isSuccessful()){
                    notifySucceded(response.body().getQuestions());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
                notifyFailed();

            }
        });
    }



    private void cancelCurrentFetchIfActive(){
        if (call != null && !call.isCanceled() && !call.isExecuted()){
            call.cancel();
        }
    }

    private void notifySucceded(List<Question> questions){
        List<Question> unModifiableQuestions = Collections.unmodifiableList(questions);
      for (Listener listener:getListeners()){
          listener.onFetchOfQuestionsSucceded(unModifiableQuestions);
      }
    }

    private void notifyFailed(){
        for (Listener listener: getListeners()){
            listener.onFetchOfQuestionsFailed();
        }
    }
}
