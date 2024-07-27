package com.example.fetchingdatastackoverflow.detailsQuestion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fetchingdatastackoverflow.R;
import com.example.fetchingdatastackoverflow.common.BaseActivity;
import com.example.fetchingdatastackoverflow.common.DialogManager;
import com.example.fetchingdatastackoverflow.MyApplication;
import com.example.fetchingdatastackoverflow.common.ServerErrorDialogFragment;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionDetailsUseCase;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionsListUseCase;
import com.example.fetchingdatastackoverflow.questions.QuestionWithBody;

import retrofit2.Retrofit;

public class QuestionDetailActivity extends BaseActivity implements QuestionDetailViewMVC.Listener, FetchQuestionDetailsUseCase.Listener {


    public static void start(Context context,String questionId){
        Intent i = new Intent(context,QuestionDetailActivity.class);
        i.putExtra(EXTRA_QUESTION_ID,questionId);
        context.startActivity(i);

    }

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"; ;
    private String mQuestionId;
    private QuestionDetailViewMVC mViewMvc;
    private DialogManager dialogManager;

    private FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mViewMvc = new QuestionDetailViewMVCImpl(LayoutInflater.from(this),null);
        setContentView(mViewMvc.getRootView());


        // Networking

        fetchQuestionDetailsUseCase =  getCompositionRoot().getFetchQuestionDetailsUseCase();

        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);


        // dialog error
        dialogManager = getCompositionRoot().getDialogManagerFactory().newDialogManager(getSupportFragmentManager());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        fetchQuestionDetailsUseCase.registerListener(this);
        fetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(mQuestionId);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
         fetchQuestionDetailsUseCase.unregisterListener(this);
    }


    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question) {

        mViewMvc.bindQuestion(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        dialogManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(),"");

    }
}