package com.example.fetchingdatastackoverflow.questionsList;

import com.example.fetchingdatastackoverflow.Question;

import java.util.List;
import java.util.Observable;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {


    interface Listener {
        void onQuestionClicked(Question question);

    }

    void bindQuestions(List<Question> questions);

}
