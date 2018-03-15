package com.example.ajender.locationtracker;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public  class ProfileFragment extends android.support.v4.app.Fragment {
    public static ImageView iv;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile_fragment, container, false);
        iv=(ImageView)v.findViewById(R.id.profileimage);
        //new DownloadImageTask((ImageView)v.findViewById(R.id.profileimage))
        //        .execute(Profile.getPhotourl());
        iv.setImageBitmap(Profile.getProfilebmp());
        TextView di=(TextView)v.findViewById(R.id.displayname);
        di.setText(Profile.name);
        TextView dp=(TextView)v.findViewById(R.id.displayphonenumber);
        dp.setText(Profile.phone);
        // Inflate the layout for this fragment
        return v;
    }

}
