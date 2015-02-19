package com.princecoder.asynctaskrotation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.princecoder.asynctaskrotation.model.Actors;
import com.princecoder.asynctaskrotation.presenter.Presenter;
import com.princecoder.asynctaskrotation.utils.L;

import java.util.ArrayList;


/**
 * @author prinzlyngotoum
 */
public class MainActivity extends Activity implements IActivity {

    private ArrayList<Actors> mActorsList;
    private ListView mlistview;
    private ActorAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private PlaceHolderFragment mFragment;
    private Presenter mPresenter;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActorsList = new ArrayList<Actors>();

        if (savedInstanceState == null) {
            mFragment = new PlaceHolderFragment();
            getFragmentManager().beginTransaction().add(mFragment, "placeHolderFragment").commit();
        } else {
            mFragment = (PlaceHolderFragment) getFragmentManager().findFragmentByTag("placeHolderFragment");
            if (savedInstanceState.getSerializable("listOfActorSaved") != null) {
                setLisOfActors((ArrayList<Actors>) savedInstanceState.getSerializable("listOfActorSaved"));
            }
        }
        //Initialize the adapter
        mAdapter = new ActorAdapter(getApplicationContext(), R.layout.row, getLisOfActors());

        //Initialize the presenter
        mPresenter=new Presenter(mFragment,this);

        //Set UIs
        onUiCreated();

    }

    /**
     * Get the list of Actors
     *
     * @return
     */
    @Override
    public ArrayList<Actors> getLisOfActors() {
        return mActorsList;
    }


    /**
     * Set the liste of actors
     *
     * @param list
     */
    public void setLisOfActors(ArrayList<Actors> list) {
        mActorsList = list;
    }

    /**
     * set UI elements
     */
    public void onUiCreated() {
        mBtnSubmit = (Button) findViewById(R.id.btnSubmit);
        mlistview = (ListView) findViewById(R.id.list);

        // Set the adapter

        mlistview.setAdapter(mAdapter);

        // listerners

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragment.isAdded()) {//Check if the fragment is added
                    //I use the presenter to perform task
                    mPresenter.performTask();
//                    mFragment.performOperations();
                }
            }
        });

        mlistview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                mPresenter.displayMessage(mActorsList.get(position).getName());
//                L.toast(getApplicationContext(), mActorsList.get(position).getName());
            }
        });

        if (mFragment != null) {
            if (mFragment.mMyTask != null
                    && mFragment.mMyTask.getStatus() == AsyncTask.Status.RUNNING) {// If the task is running
                L.m("display " + mFragment.mMyTask);
                mProgressDialog = ProgressDialog.show(this, "Fetching", "Please wait !!!");
            } else { //The task is not running any more
                L.m("remove " + mFragment.mMyTask);
                afterFetching();
            }
        } else {
            L.toast(getApplicationContext(), "Fragment is null");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the list of actors
        outState.putSerializable("listOfActorSaved", getLisOfActors());
    }

    @Override
    public void updateAdapter(boolean result) {
        mAdapter.notifyDataSetChanged();
        if (result == false)
            L.toast(getApplicationContext(), "Unable to fetch data from server");
    }

    @Override
    public void beforeFetching() {
        mProgressDialog = ProgressDialog.show(this, "Working", "Please wait !!!");
    }

    @Override
    public void afterFetching() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    //To avoid the :Activity has leaked window com.android.internal.policy... exception
    @Override
    public void Detach() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
