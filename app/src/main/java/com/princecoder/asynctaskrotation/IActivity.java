package com.princecoder.asynctaskrotation;

/**
 * Created by prinzlyngotoum on 12/4/14.
 */
public interface IActivity {

    public void updateAdapter(boolean result);

    public void beforeFetching();

    public void afterFetching();
}
