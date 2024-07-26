package com.example.fetchingdatastackoverflow.detailsQuestion;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fetchingdatastackoverflow.questions.QuestionWithBody;
import com.example.fetchingdatastackoverflow.R;
import com.example.fetchingdatastackoverflow.questionsList.BaseViewMVC;

public class QuestionDetailViewMVCImpl extends BaseViewMVC<QuestionDetailViewMVC.Listener>
implements QuestionDetailViewMVC{

    private final TextView mTxtQuestionBody;

    public QuestionDetailViewMVCImpl(LayoutInflater inflater, ViewGroup container){
        setRootView(inflater.inflate(R.layout.activity_question_detail,container,false));
        mTxtQuestionBody =  findViewById(R.id.tv_answer);

    }

    @Override
    public void bindQuestion(QuestionWithBody question) {
        String questionBody = question.getmBody();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            mTxtQuestionBody.setText(Html.fromHtml(questionBody,Html.FROM_HTML_MODE_LEGACY));

        } else {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
    }
}
