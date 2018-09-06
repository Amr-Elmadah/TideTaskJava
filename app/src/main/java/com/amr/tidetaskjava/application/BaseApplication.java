package com.amr.tidetaskjava.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class BaseApplication extends Application {

    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
