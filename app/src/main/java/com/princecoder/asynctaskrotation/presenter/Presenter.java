package com.princecoder.asynctaskrotation.presenter;

import android.content.Context;

import com.princecoder.asynctaskrotation.PlaceHolderFragment;
import com.princecoder.asynctaskrotation.utils.L;

/**
 * Created by prinzlyngotoum on 2/18/15.
 */
public class Presenter implements IPresenter {

    PlaceHolderFragment myFragment;
    Context mContext;

    public Presenter(PlaceHolderFragment fragment, Context context){
        this.myFragment=fragment;
        this.mContext=context;
    }
    @Override
    public void performTask() {
        myFragment.performOperations();
    }

    @Override
    public void displayMessage(String s) {
        L.toast(mContext, s);
    }
}
