package com.example.android.registrationapp;
import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by win on 18/11/2016.
 */
public class FireApp extends Application {
    public String UID ;
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
