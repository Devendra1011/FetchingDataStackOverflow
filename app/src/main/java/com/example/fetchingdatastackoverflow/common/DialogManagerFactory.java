package com.example.fetchingdatastackoverflow.common;

import androidx.fragment.app.FragmentManager;

public class DialogManagerFactory {
    public  DialogManager newDialogManager(FragmentManager fragmentManager){
        return new DialogManager(fragmentManager);
    }
}
