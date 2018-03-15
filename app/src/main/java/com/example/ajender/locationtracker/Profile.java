package com.example.ajender.locationtracker;
import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ajay on 12-09-2015.
 */
public class Profile implements Serializable {
    //private static final long serialVersionUID=2L;
    public static Image photo;
    public static boolean isloggedin=false;
    public static String name;
    public static String photourl;
    public static String email;
    public static String phone;
    public static Bitmap profilebmp;
    public static String[] frndlist;
    public static String getName(){ return name;}
    public static String getPhotourl(){return photourl;}
    public static String getEmail(){
        return email;
    }
    public static String getPhone(){return phone;}
    public static String[] getFrndList(){ return Profile.frndlist;}
    public static Bitmap getProfilebmp(){return Profile.profilebmp;}

}
