package com.example.fetchingdatastackoverflow.questions;

import com.google.gson.annotations.SerializedName;

public class Question {

    // we will configure it later
    // we will make it calling retrofit call


    @SerializedName("title")
    private final String Title;

    @SerializedName("question_id")
    private final String Id;


    // Constructor
    public Question(String Title, String Id) {
        this.Title = Title;
        this.Id = Id;
    }

    // Getter


    public String getTitle() {
        return Title;
    }

    public String getId() {
        return Id;
    }
}
