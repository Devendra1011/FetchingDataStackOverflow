package com.example.fetchingdatastackoverflow.detailsQuestion;

import com.example.fetchingdatastackoverflow.questions.QuestionWithBody;
import com.example.fetchingdatastackoverflow.questionsList.ObservableViewMvc;

public interface QuestionDetailViewMVC extends ObservableViewMvc<QuestionDetailViewMVC.Listener> {
    interface Listener{

    }

    void bindQuestion(QuestionWithBody question);
}
