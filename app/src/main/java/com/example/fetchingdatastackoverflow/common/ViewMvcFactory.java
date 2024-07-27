package com.example.fetchingdatastackoverflow.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.fetchingdatastackoverflow.detailsQuestion.QuestionDetailViewMVC;
import com.example.fetchingdatastackoverflow.detailsQuestion.QuestionDetailViewMVCImpl;
import com.example.fetchingdatastackoverflow.questionsList.QuestionsListViewMVCImpl;
import com.example.fetchingdatastackoverflow.questionsList.QuestionsListViewMvc;
import com.example.fetchingdatastackoverflow.questionsList.ViewMvc;

public class ViewMvcFactory {
    private final LayoutInflater layoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    // New Instance
    public <T extends ViewMvc> T newInstance(Class<T> myViewClass, @Nullable ViewGroup container){
        ViewMvc viewMvc;
        if (myViewClass == QuestionsListViewMvc.class){
            viewMvc = new QuestionsListViewMVCImpl(layoutInflater,container);

        } else if (myViewClass == QuestionDetailViewMVC.class){
            viewMvc = new QuestionDetailViewMVCImpl(layoutInflater,container);

        } else {
            throw new IllegalArgumentException("Unsupported MVC view class"+myViewClass);

        }

        return (T) viewMvc;
    }
}
