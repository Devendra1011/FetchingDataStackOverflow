package com.example.fetchingdatastackoverflow.common;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchingdatastackoverflow.MyApplication;
import com.example.fetchingdatastackoverflow.dependencyInjection.CompositionRoot;

public class BaseActivity extends AppCompatActivity {
    protected CompositionRoot getCompositionRoot(){
        return ((MyApplication) getApplication()).getCompositionRoot();


    }

}
