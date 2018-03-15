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
public class RequestLoginGetFrndList extends AsyncTask<Pair<Context,String>,Void,String> {
    public int success=-1;
    private Context context;
    HttpURLConnection con;
    String[] datatosend={Profile.getName(),Profile.getEmail(),Profile.getPhone()};
    @Override
    protected String doInBackground(Pair<Context,String>... params){
        String urln ="https://strong-aegis-106207.appspot.com/db";
        context=params[0].first;
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
            Profile.frndlist=(String[])ois.readObject();
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
        MainActivity.sync1.setChecked(true);
        super.onPostExecute(s);
        Toast.makeText(context,"Success  "+s,Toast.LENGTH_LONG).show();

    }
}
