package com.example.fetchingdatastackoverflow;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.fetchingdatastackoverflow.detailsQuestion.QuestionDetailActivity;
import com.example.fetchingdatastackoverflow.detailsQuestion.QuestionDetailViewMVC;
import com.example.fetchingdatastackoverflow.networking.QuestionsListResponseSchema;
import com.example.fetchingdatastackoverflow.networking.StackoverflowApi;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionDetailsUseCase;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionsListUseCase;
import com.example.fetchingdatastackoverflow.questions.Question;
import com.example.fetchingdatastackoverflow.questionsList.QuestionsListViewMVCImpl;
import com.example.fetchingdatastackoverflow.questionsList.QuestionsListViewMvc;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends AppCompatActivity implements QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {




    private static final int NUM_OF_QUESTIONS_ID_TO_FETCH = 20;
    private QuestionsListViewMvc mViewMVC;
    private FetchQuestionsListUseCase fetchQuestionsListUseCase;





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

        // Networking
        fetchQuestionsListUseCase = new FetchQuestionsListUseCase();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        fetchQuestionsListUseCase.registerListener(this);
        fetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_ID_TO_FETCH);
    }

    @Override
    protected void onStop() {

        super.onStop();
        mViewMVC.unregisterListener(this);
        fetchQuestionsListUseCase.unregisterListener(this);

    }

    @Override
    public void onFetchOfQuestionsSucceded(List<Question> questions) {
        mViewMVC.bindQuestions(questions);
    }

    @Override
    public void onFetchOfQuestionsFailed() {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(ServerErrorDialogFragment.newInstance(),null).commitAllowingStateLoss();


    }

    @Override
    public void onQuestionClicked(Question question) {

        QuestionDetailActivity.start(QuestionsListActivity.this,question.getId());


    }
}