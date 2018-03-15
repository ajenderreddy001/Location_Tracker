package com.example.ajender.locationtracker;

/**
 * Created by Ajay on 27-09-2015.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Ajay on 18-09-2015.
 */
public class ShareLocationAsync extends AsyncTask<Pair<Context,String>,Void,String> {
    public int success=-1;
    private Context context;
    HttpURLConnection con;
    String datatosend;
    @Override
    protected String doInBackground(Pair<Context,String>... params){
        String urln ="https://strong-aegis-106207.appspot.com/map";
        context=params[0].first;
        datatosend=params[0].second;
        try {
            URL url = new URL(urln);
            con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            ObjectOutputStream oos=new ObjectOutputStream(new BufferedOutputStream(con.getOutputStream()));
            oos.reset();
            oos.writeObject(datatosend);
            oos.flush();
            oos.close();
            ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(con.getInputStream()));
            HomeFragment.coor=(String[])ois.readObject();
            ois.close();
            success=1;
            return success+"";
        }
        catch(Exception e){
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String s) {
        boolean b=HomeFragment.impsync.isChecked();
        if(b){
            HomeFragment.impsync.setChecked(false);
            HomeFragment.impsync.setChecked(true);
        }
        else{
            HomeFragment.impsync.setChecked(true);
        }
        super.onPostExecute(s);
        Toast.makeText(context,"Success  "+s,Toast.LENGTH_LONG).show();

    }
}
