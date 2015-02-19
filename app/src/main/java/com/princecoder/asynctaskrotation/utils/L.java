package com.princecoder.asynctaskrotation.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by prinzlyngotoum on 12/2/14.
 * This class handle logs
 */
public class L {

    /**
     * To log a specific information
     * @param message
     */
    public static void m(String message) {
        Log.d("princeCoder", message);
    }

    /**
     * Display a Toast message
     * @param c --> The Context
     * @param message --> Message to get displayed
     */
    public static void toast(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_LONG).show();
    }
}
