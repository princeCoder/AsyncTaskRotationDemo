package com.princecoder.asynctaskrotation;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by prinzlyngotoum on 12/4/14.
 */
public class PlaceHolderFragment extends Fragment {

    IActivity mActivity;
    JSONAsyncTask mMyTask;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (IActivity) activity;
        if (mMyTask != null) {
            mMyTask.onAttach((IActivity) activity);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        mMyTask.onDetach();
        super.onDetach();
    }

    /**
     * Call the AsyncTask to fetch data
     */
    public void performOperations() {
        mMyTask = new JSONAsyncTask(mActivity);
        mMyTask.execute(getResources().getString(R.string.url));
    }
}
