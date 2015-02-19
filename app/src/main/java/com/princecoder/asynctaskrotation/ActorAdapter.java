package com.princecoder.asynctaskrotation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.princecoder.asynctaskrotation.model.Actors;
import com.princecoder.asynctaskrotation.utils.ViewHolder;

import java.io.InputStream;
import java.util.ArrayList;

public class ActorAdapter extends ArrayAdapter<Actors> {
    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public ActorAdapter(Context context, int resource, ArrayList<Actors> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            v = vi.inflate(Resource, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.imageview.setImageResource(R.drawable.ic_launcher);
        new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.tvName.setText(actorList.get(position).getName());
        holder.tvDescription.setText(actorList.get(position).getDescription());
        holder.tvDOB.setText("B'day: " + actorList.get(position).getDob());
        holder.tvCountry.setText(actorList.get(position).getCountry());
        holder.tvHeight.setText("Height: " + actorList.get(position).getHeight());
        holder.tvSpouse.setText("Spouse: " + actorList.get(position).getSpouse());
        holder.tvChildren.setText("Children: " + actorList.get(position).getChildren());
        return v;
    }
}

/**
 * Used to download images
 */
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView mImage;

    public DownloadImageTask(ImageView bmImage) {
        this.mImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        mImage.setImageBitmap(result);
    }
}