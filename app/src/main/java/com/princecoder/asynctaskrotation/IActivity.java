package com.princecoder.asynctaskrotation;

import com.princecoder.asynctaskrotation.model.Actors;

import java.util.List;

/**
 * Created by prinzlyngotoum on 12/4/14.
 */
public interface IActivity {

    public void updateAdapter(boolean result);

    public void beforeFetching();

    public void afterFetching();

    public void Detach();

    public List<Actors> getLisOfActors();
}
