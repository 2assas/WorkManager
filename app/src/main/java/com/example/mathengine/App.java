package com.example.mathengine;
import android.app.Application;
import android.content.Context;

import androidx.work.WorkManager;

class App extends Application implements androidx.work.Configuration.Provider {


    @Override
     public androidx.work.Configuration getWorkManagerConfiguration() {
         return new androidx.work.Configuration.Builder()
                 .setMinimumLoggingLevel(android.util.Log.INFO)
                 .build();
     }
     //initialize WorkManager

    @Override
    public void onCreate() {
        super.onCreate();
        WorkManager.initialize(this, getWorkManagerConfiguration());
    }
}