package com.example.ajender.locationtracker;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareLocationFragment extends android.support.v4.app.Fragment {


    public ShareLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.share_location_fragment, container, false);
        ToggleButton sb=(ToggleButton)v.findViewById(R.id.sharebutton);
        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //share
                }
                else{
                    //donot share
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }


}
