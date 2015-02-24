package com.princecoder.asynctaskrotation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.princecoder.asynctaskrotation.utils.L;

/**
 * Created by prinzlyngotoum on 12/4/14.
 */
public class PlaceHolderFragment extends Fragment {

    IActivity mActivity;
    JSONAsyncTask mMyTask;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setActivity((IActivity) activity);
        if (mMyTask != null) {// if the task is activ, I attach the reference of the new activity, because the activity will get destroyed
            mMyTask.onAttach((IActivity) activity);
        }
        else { // If there is no task, then I initialize my task.
            mMyTask = new JSONAsyncTask(mActivity);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);// I don't want the fragment to get destroyed
    }

    @Override
    public void onDetach() {
        mMyTask.onDetach();
        super.onDetach();
    }


    public void setActivity(IActivity activity){
        this.mActivity=activity;
    }

    /**
     * Call the AsyncTask to fetch data
     */
    public void performOperations() {
        if (isOnline()) {
            requestData();
        } else {
            L.m("Network isn't available");
        }

    }

    /**
     * Are we online?
     *
     * @return
     */
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void requestData() {
        mMyTask.execute(getResources().getString(R.string.url));
    }

}
