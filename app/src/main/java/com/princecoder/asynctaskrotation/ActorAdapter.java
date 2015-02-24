package com.princecoder.asynctaskrotation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.princecoder.asynctaskrotation.model.Actors;
import com.princecoder.asynctaskrotation.utils.L;
import com.princecoder.asynctaskrotation.utils.ViewHolder;

import java.io.InputStream;
import java.util.ArrayList;

public class ActorAdapter extends ArrayAdapter<Actors> {
    private ArrayList<Actors> actorList;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    private LruCache<Integer,Bitmap> imageCache;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public ActorAdapter(Context context, int resource, ArrayList<Actors> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize =maxMemory/8;
        imageCache=new LruCache<>(cacheSize);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
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

        //holder.imageview.setImageResource(R.drawable.ic_launcher);
        final Actors actor=actorList.get(position);
        holder.tvName.setText(actor.getName());
        holder.tvDescription.setText(actor.getDescription());
        holder.tvDOB.setText("B'day: " + actor.getDob());
        holder.tvCountry.setText(actor.getCountry());
        holder.tvHeight.setText("Height: " + actor.getHeight());
        holder.tvSpouse.setText("Spouse: " + actor.getSpouse());
        holder.tvChildren.setText("Children: " + actor.getChildren());

        Bitmap bitmap= imageCache.get(actor.getActorId());
        if (bitmap != null) {
            holder.imageview.setImageBitmap(bitmap);
        }
        else {
            new DownloadImageTask(holder.imageview).execute(actor);
        }
        return v;
    }

    /**
     * Used to download images
     */
    class DownloadImageTask extends AsyncTask<Actors, Void, Bitmap> {
        ImageView mImage;
        Actors act;

        public DownloadImageTask(ImageView bmImage) {
            this.mImage = bmImage;
        }

        protected Bitmap doInBackground(Actors... actor) {
            act=actor[0];
            String urlDisplay = act.getImage();
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                L.m(e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        protected void onPostExecute(Bitmap result) {
            mImage.setImageBitmap(result);
            //I reset the cache
            imageCache.put(act.getActorId(), result);
        }
    }
}

