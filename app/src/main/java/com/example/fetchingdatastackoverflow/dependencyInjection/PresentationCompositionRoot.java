package com.example.fetchingdatastackoverflow.dependencyInjection;

import android.view.LayoutInflater;

import androidx.fragment.app.FragmentManager;

import com.example.fetchingdatastackoverflow.common.DialogManager;
import com.example.fetchingdatastackoverflow.common.ViewMvcFactory;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionDetailsUseCase;
import com.example.fetchingdatastackoverflow.questions.FetchQuestionsListUseCase;

public class PresentationCompositionRoot {

    private final CompositionRoot compositionRoot;
    private final FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;

    public PresentationCompositionRoot(CompositionRoot compositionRoot, FragmentManager fragmentManager,LayoutInflater layoutInflater){
        this.compositionRoot = compositionRoot;
        this.fragmentManager = fragmentManager;
        this.layoutInflater = layoutInflater;
    }

    public DialogManager getDialogManager(){
        return new DialogManager(fragmentManager);
    }


    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase(){
        return compositionRoot.getFetchQuestionDetailsUseCase();
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase(){
        return compositionRoot.getFetchQuestionsListUseCase();
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(layoutInflater);
    }

}
