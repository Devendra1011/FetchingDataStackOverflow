package com.example.fetchingdatastackoverflow.networking;

import com.example.fetchingdatastackoverflow.questions.Question;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionsListResponseSchema {
    @SerializedName("items")
    private final List<Question> mQuestions;


    public QuestionsListResponseSchema(List<Question> questions){
        mQuestions= questions;
    }

    // getter

    public List<Question> getQuestions() {
        return mQuestions;
    }
}
