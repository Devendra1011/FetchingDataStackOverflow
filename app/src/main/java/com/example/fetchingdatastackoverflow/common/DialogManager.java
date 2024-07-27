package com.example.fetchingdatastackoverflow.common;


import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@UiThread
public class DialogManager {
    public static final String ARGUMENT_DIALOG_ID = "ARGUMENT_DIALOG_ID";
    public static final String DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG";

    private FragmentManager fragmentManager;
    private DialogFragment currentlyShownDialog;


    public DialogManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        Fragment fragmentWithDialogTag  = fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG);
        if (fragmentWithDialogTag != null && DialogFragment.class.isAssignableFrom(
                fragmentWithDialogTag.getClass())){
            currentlyShownDialog = (DialogFragment) fragmentWithDialogTag;

        }


    }

    @Nullable
    public DialogFragment getmCurrentlyShownDialog(){
        return currentlyShownDialog;
    }

    public String getCurrentlyShownDialog(){
        if (getmCurrentlyShownDialog() == null || currentlyShownDialog.getArguments() == null ||
        !currentlyShownDialog.getArguments().containsKey(ARGUMENT_DIALOG_ID)){
            return "";
        } else {
            return currentlyShownDialog.getArguments().getString(ARGUMENT_DIALOG_ID);

        }

    }

    public boolean isDialogCurrentlyShown(String id){
        String shownDialogId = getCurrentlyShownDialog();
        return !TextUtils.isEmpty(shownDialogId) && shownDialogId.equals(id);
    }

    public void dismissCurrentlyShownDialog(){
        if (currentlyShownDialog != null){
            currentlyShownDialog.dismissAllowingStateLoss();
            currentlyShownDialog = null;

        }
    }

    public void showRetainedDialogWithId(DialogFragment dialogFragment,@Nullable String id){
        dismissCurrentlyShownDialog();
        dialogFragment.setRetainInstance(true);
        setId(dialogFragment,id);
        showDialog(dialogFragment);
    }

    public void setId(DialogFragment dialog,String id){
        Bundle args = dialog.getArguments() != null ? dialog.getArguments() : new Bundle(1);
        args.putString(ARGUMENT_DIALOG_ID,id);
        dialog.setArguments(args);
    }

    private void showDialog(DialogFragment dialog){
        fragmentManager.beginTransaction().add(dialog,DIALOG_FRAGMENT_TAG).commitAllowingStateLoss();
        currentlyShownDialog = dialog;
    }
}
