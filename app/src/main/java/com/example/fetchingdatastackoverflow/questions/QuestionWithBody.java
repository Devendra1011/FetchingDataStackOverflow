package com.example.fetchingdatastackoverflow.questions;

import com.google.gson.annotations.SerializedName;

public class QuestionWithBody {
    @SerializedName("title")
    private final String mTitle;


    @SerializedName("question_id")
    private final String mId;

    @SerializedName("body")
    private final String mBody;


    // constructor
    public QuestionWithBody(String mTitle, String mId, String mBody) {
        this.mTitle = mTitle;
        this.mId = mId;
        this.mBody = mBody;
    }

    // getter

    public String getmTitle() {
        return mTitle;
    }

    public String getmId() {
        return mId;
    }

    public String getmBody() {
        return mBody;
    }
}
