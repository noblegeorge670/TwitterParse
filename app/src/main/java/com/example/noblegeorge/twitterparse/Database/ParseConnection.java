package com.example.noblegeorge.twitterparse.Database;

import android.app.Application;
import android.service.autofill.SaveCallback;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;

import java.text.ParseException;

/**
 * Created by noblegeorge on 2018-03-29.
 */

public class ParseConnection extends Application {



    public void onCreate() {
        super.onCreate();


        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("dbbe954d58f7ece33685be472ffcf879e851cb31")
                .clientKey("e8813a5c5c2fe136879f9265d0b33863f1b0714f")
                .server("http://18.217.54.120:80/parse/")
                .build()
        );




        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }




}
