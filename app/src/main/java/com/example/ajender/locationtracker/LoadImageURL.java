package com.example.ajender.locationtracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by Ajay on 28-09-2015.
 */
public class LoadImageURL extends AsyncTask<Pair<Context,String>, Void, String> {
    public  Context context;
    public LoadImageURL() {

    }

    protected String doInBackground(Pair<Context,String>... params) {
        String s="";
        String urldisplay = params[0].second;
        context=params[0].first;
        Bitmap tempbmp=null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inScaled=false;
            Rect out=new Rect();
            tempbmp = BitmapFactory.decodeStream(in,out,options);
            Profile.profilebmp=tempbmp;
            s="Success";
            return s;
        } catch (Exception e) {
            Log.e("Image Error HERE", e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }


    }

    protected void onPostExecute(String s) {
       MainActivity.sync2.setChecked(true);
        super.onPostExecute(s);
        Toast.makeText(context, "Success  " + s, Toast.LENGTH_LONG).show();

    }
}
