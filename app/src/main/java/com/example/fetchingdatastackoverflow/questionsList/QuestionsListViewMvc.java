package com.example.fetchingdatastackoverflow.questionsList;

import com.example.fetchingdatastackoverflow.questions.Question;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {


    interface Listener {
        void onQuestionClicked(Question question);

    }

    void bindQuestions(List<Question> questions);

}
