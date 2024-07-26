package com.example.fetchingdatastackoverflow.detailsQuestion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.fetchingdatastackoverflow.Constants;
import com.example.fetchingdatastackoverflow.R;
import com.example.fetchingdatastackoverflow.ServerErrorDialogFragment;
import com.example.fetchingdatastackoverflow.networking.SingleQuestionResponseSchema;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDetailActivity extends AppCompatActivity implements Callback<SingleQuestionResponseSchema>,QuestionDetailViewMVC.Listener {


    public static void start(Context context,String questionId){
        Intent i = new Intent(context,QuestionDetailActivity.class);
        i.putExtra(EXTRA_QUESTION_ID,questionId);
        context.startActivity(i);

    }

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";
    private TextView mTxtQuestionBody;
    private StackoverflowApi stackoverflowApi;
    private String mQuestionId;
    private Call<SingleQuestionResponseSchema> mCall;


    private QuestionDetailViewMVC mViewMvc;




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

        mTxtQuestionBody = findViewById(R.id.tv_answer);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        stackoverflowApi = retrofit.create(StackoverflowApi.class);

        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mCall = stackoverflowApi.questionDetails(mQuestionId);
        mCall.enqueue(this);

    }


    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        if (mCall != null){
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
        SingleQuestionResponseSchema questionResponseSchema;


        if (response.isSuccessful() && (questionResponseSchema = response.body()) != null ){

            String questionBody = questionResponseSchema.getQuestions().getmBody();


                mViewMvc.bindQuestion(questionResponseSchema.getQuestions());



        }else {
            onFailure(call,null);
        }
    }

    @Override
    public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(ServerErrorDialogFragment.newInstance(),null).commitAllowingStateLoss();
    }
}