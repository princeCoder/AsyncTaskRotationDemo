package com.princecoder.asynctaskrotation;

import android.net.ParseException;
import android.os.AsyncTask;

import com.princecoder.asynctaskrotation.model.Actors;
import com.princecoder.asynctaskrotation.parsers.ActorJSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by prinzlyngotoum on 12/4/14.
 */
public class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

    IActivity mActivity;

    public JSONAsyncTask(IActivity activity) {
        onAttach(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mActivity != null) {
            mActivity.beforeFetching();
        }
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int status = urlConnection.getResponseCode();
            if (status == 200) {
                String content = HttpManager.getData(urlConnection);
                List<Actors> actorsList = ActorJSONParser.parseFeed(content);
                for (Actors actor : actorsList) {
                    mActivity.getLisOfActors().add(actor);
                }
                return true;
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    protected void onPostExecute(Boolean result) {
        if (mActivity != null) {// I make the test because for some reasons, it can happen that when the activity get destroy, we don't have the reference of the new one, just for some milliseconds
            mActivity.updateAdapter(result);
            mActivity.afterFetching();
        }
    }

    /**
     * Called when the fragment is attached to the activity
     */
    public void onAttach(IActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Called when the fragment is detached to the activity
     */
    public void onDetach() {
//        ((MainActivity) mActivity).Detach();
        mActivity.Detach();
        mActivity = null;
    }

}