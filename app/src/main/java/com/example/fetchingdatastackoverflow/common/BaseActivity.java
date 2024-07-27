package com.example.fetchingdatastackoverflow.common;

import android.view.LayoutInflater;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchingdatastackoverflow.MyApplication;
import com.example.fetchingdatastackoverflow.dependencyInjection.CompositionRoot;
import com.example.fetchingdatastackoverflow.dependencyInjection.PresentationCompositionRoot;

public class BaseActivity extends AppCompatActivity {



    private PresentationCompositionRoot presentationCompositionRoot;


    @UiThread
    protected PresentationCompositionRoot getCompositionRoot(){
        if (presentationCompositionRoot == null){
            presentationCompositionRoot = new PresentationCompositionRoot(
                    getAppCompositionRoot(),getSupportFragmentManager(), LayoutInflater.from(this)
            );
        }
        return presentationCompositionRoot;
    }

    private CompositionRoot getAppCompositionRoot(){
        return ((MyApplication) getApplication()).getCompositionRoot();
    }

}
