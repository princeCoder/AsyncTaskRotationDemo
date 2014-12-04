package com.princecoder.asynctaskrotation;

import android.net.ParseException;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String data = convertInputStreamToString(in);

                JSONObject jsono = new JSONObject(data);
                JSONArray jarray = jsono.getJSONArray("actors");

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject object = jarray.getJSONObject(i);
                    Thread.sleep(1000);

                    Actors actor = new Actors();

                    actor.setName(object.getString("name"));
                    actor.setDescription(object.getString("description"));
                    actor.setDob(object.getString("dob"));
                    actor.setCountry(object.getString("country"));
                    actor.setHeight(object.getString("height"));
                    actor.setSpouse(object.getString("spouse"));
                    actor.setChildren(object.getString("children"));
                    actor.setImage(object.getString("image"));

                    L.m("----------- Element " + i);
                    ((MainActivity) mActivity).getLisOfActors().add(actor);
                }
                return true;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {
        if (mActivity != null) {
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
        ((MainActivity) mActivity).Detach();
        mActivity = null;
    }


    /**
     * Convert an inputStream to String
     *
     * @param inputStream
     * @return
     * @throws java.io.IOException
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder result = new StringBuilder("");
        while ((line = bufferedReader.readLine()) != null)
            result.append(line);
        inputStream.close();
        return result.toString();
    }

}