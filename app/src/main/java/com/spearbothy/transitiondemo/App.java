package com.spearbothy.transitiondemo;

import android.app.Application;

/**
 * Created by mahao on 17-5-19.
 */

public class App extends Application {

    public  static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }


}
