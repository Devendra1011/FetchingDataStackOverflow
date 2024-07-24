package com.example.fetchingdatastackoverflow;

import com.google.gson.annotations.SerializedName;

public class SingleQuestionResponseSchema {
    @SerializedName("items")
    private final List<QuestionWithBody> mQuestions;


    // constructor
    public SingleQuestionResponseSchema(List<QuestionWithBody> mQuestions) {
        this.mQuestions = mQuestions;
    }


    // getter
    public QuestionWithBody getmQuestions() {
        return mQuestions.get(0);
    }
}
