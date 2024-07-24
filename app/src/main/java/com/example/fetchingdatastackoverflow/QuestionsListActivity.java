package com.example.fetchingdatastackoverflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchingdatastackoverflow.questionsList.QuestionsListViewMVCImpl;
import com.example.fetchingdatastackoverflow.questionsList.QuestionsListViewMvc;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends AppCompatActivity implements Callback<QuestionsListResponseSchema>, QuestionsListViewMvc.Listener {




    private StackoverflowApi mStackoverflowApi;
    private Call<QuestionsListResponseSchema> mCall;
    private QuestionsListViewMvc mViewMVC;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this),null);
        setContentView(mViewMVC.getRootView());

        // Initializing Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        mStackoverflowApi = retrofit.create(StackoverflowApi.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        mCall = mStackoverflowApi.lastActiveQuestions(20);
        mCall.enqueue(this);
    }

    @Override
    protected void onStop() {

        super.onStop();
        mViewMVC.unregisterListener(this);
        if (mCall != null){
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;
        if (response.isSuccessful() && (responseSchema = response.body()) != null){
            mViewMVC.bindQuestions(responseSchema.getQuestions());

        } else {
            onFailure(call,null);
        }

    }

    @Override
    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
        QuestionsListResponseSchema responseSchema;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(ServerErrorDialogFragment.newInstance(),null).commitAllowingStateLoss();

    }

    @Override
    public void onQuestionClicked(Question question) {

        QuestionDetailActivity.start(QuestionsListActivity.this,question.getId());


    }
}