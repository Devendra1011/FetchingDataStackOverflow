package com.example.fetchingdatastackoverflow.networking;

import com.example.fetchingdatastackoverflow.questions.QuestionWithBody;
import com.google.gson.annotations.SerializedName;


import java.util.Collections;
import java.util.List;

public class SingleQuestionResponseSchema {
    @SerializedName("items")
    private final List<QuestionWithBody> mQuestions;


    // constructor
    public SingleQuestionResponseSchema(QuestionWithBody questions) {
        mQuestions = Collections.singletonList(questions);
    }


    // getter
    public QuestionWithBody getQuestions() {
        return mQuestions.get(0);
    }
}
