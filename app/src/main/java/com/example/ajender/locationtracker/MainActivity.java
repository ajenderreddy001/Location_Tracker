package com.example.ajender.locationtracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.media.Image;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG="MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final String KEY_IS_RESOLVING = "is_resolving";
    private static final String KEY_SHOULD_RESOLVE = "should_resolve";
    public GoogleApiClient mGoogleApiClient;
    private TextView mStatus;
    public static boolean mShouldResolve = false;
    public static SignInButton sib;
    public static EditText phone;
    public static Person person;
    public static CheckBox sync1,sync2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       //this will remove status bar in activity for full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        sib=(SignInButton)findViewById(R.id.sign_in_button);
        sib.setOnClickListener(this);
        sync1=(CheckBox)findViewById(R.id.sync1);
        sync2=(CheckBox)findViewById(R.id.sync2);
        sync1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   startHome();
               }
            }
        });
        sync2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startHome();
                }
            }
        });
        phone=(EditText)findViewById(R.id.editText);
        mStatus = (TextView) findViewById(R.id.textView);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }
/*
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            //shared preference
            //Profile profile=(Profile)savedInstanceState.getSerializable("Restoring");
            Intent i=new Intent(this,Main2Activity.class);
            startActivity(i);
            int a=savedInstanceState.getInt("key1");
            Toast.makeText(this,"key1 "+a,Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this,"restore",Toast.LENGTH_SHORT).show();

    }*/

    @Override
    protected void onStart() {
        super.onStart();
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(Profile.isloggedin) {
            Profile profile = new Profile();
            outState.putSerializable("Restoring", profile);
        }
        outState.putInt("key1",25);
        Toast.makeText(this,"single",Toast.LENGTH_SHORT).show();

    }*/

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Toast.makeText(this," destoying",Toast.LENGTH_SHORT).show();
        super.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further errors.
            if (resultCode != RESULT_OK) {
                mStatus.setText("Try Again");
            }
            Profile.isloggedin=true;
            mShouldResolve = false;
            mGoogleApiClient.connect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        person=Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        mStatus.setVisibility(View.VISIBLE);
        mStatus.setText("LOGGING IN");
        sib.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);
        setProfile(Plus.AccountApi.getAccountName(mGoogleApiClient));
        asyncTasking();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        if (mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mStatus.setText("sigining resolving");
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                //showErrorDialog(connectionResult);
                //mShouldResolve = false;
                Toast.makeText(this, "is resolving is false", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show the signed-out UI
            Toast.makeText(this,"No logged on",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (R.id.sign_in_button == v.getId()) {
            mStatus.setVisibility(View.VISIBLE);
            if (phone.getText().toString().length() == 10) {
                mStatus.setTextColor(0xFF096B1C);
                mStatus.setText("Signing in...");
                mShouldResolve = true;
                mGoogleApiClient.connect();
            }
            else {
                mStatus.setTextColor(0xFFFF050D);
                mStatus.setText("Please Enter 10 digit number...");
            }
        }
    }
    public void setProfile(String email){
        Profile.name=person.getDisplayName();
        Profile.photourl=person.getImage().getUrl();
        Profile.email=email;
        //Profile.photo=person.getImage();
        Profile.phone=phone.getText().toString();
    }
    public void asyncTasking(){
        new RequestLoginGetFrndList().execute(new Pair<Context, String>(this,"logging.."));
        //LoadfrndList
        new LoadImageURL().execute(new Pair<Context, String>(this,Profile.getPhotourl()));
    }
    public void startHome(){
        if(sync1.isChecked()&&sync2.isChecked()){
        Toast.makeText(this,"Welcome "+person.getDisplayName(),Toast.LENGTH_LONG).show();
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
        }
    }
}
