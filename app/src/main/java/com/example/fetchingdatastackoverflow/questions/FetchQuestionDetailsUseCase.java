package com.example.fetchingdatastackoverflow.questions;

import androidx.annotation.Nullable;

import com.example.fetchingdatastackoverflow.common.Constants;
import com.example.fetchingdatastackoverflow.networking.SingleQuestionResponseSchema;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;
import com.example.fetchingdatastackoverflow.questionsList.BaseObservable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {


    public interface Listener{
        void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question);
        void onFetchOfQuestionDetailsFailed();


    }
    private final StackoverflowApi mStackoverflowApi;


    @Nullable
    Call<SingleQuestionResponseSchema> call;

    public FetchQuestionDetailsUseCase(StackoverflowApi stackoverflowApi){
        mStackoverflowApi = stackoverflowApi;
    }

    public void fetchQuestionDetailsAndNotify(String questionId){
        cancelCurrentFetchIfActive();
        call = mStackoverflowApi.questionDetails(questionId);
        call.enqueue((new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()){
                    notifySucceded(response.body().getQuestions());

                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
                if (call != null && !call.isExecuted()){
                    call.cancel();
                }

            }

        }));
    }


    private void cancelCurrentFetchIfActive(){
        if (call != null && !call.isCanceled() && !call.isExecuted()){
            call.cancel();
        }
    }

    private void notifySucceded(QuestionWithBody question){
        for (Listener listener: getListeners()){
            listener.onFetchOfQuestionDetailsSucceeded(question);

        }
    }

    private void notifyFailed(){
        for (Listener listener: getListeners()){
            listener.onFetchOfQuestionDetailsFailed();
        }
    }
}
