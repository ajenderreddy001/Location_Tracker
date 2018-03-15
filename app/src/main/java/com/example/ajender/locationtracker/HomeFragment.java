package com.example.ajender.locationtracker;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static String[] coor;
    public static CheckBox impsync;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.home_fragment, container, false);
        ListAdapter listAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Profile.getFrndList());
        ListView frndview=(ListView)v.findViewById(R.id.frndlistview);
        frndview.setAdapter(listAdapter);
        frndview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                new ShareLocationAsync().execute(new Pair<Context, String>(getActivity(),s));
            }
        });
        impsync=(CheckBox)v.findViewById(R.id.impsync);
        impsync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startMap();
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void startMap(){
        Intent i = new Intent(getActivity(), MapsActivity.class);
        i.putExtra("coor",coor);
        startActivity(i);
    }
}
