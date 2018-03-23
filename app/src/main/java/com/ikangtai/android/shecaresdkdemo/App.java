package com.ikangtai.android.shecaresdkdemo;

import android.app.Application;

import com.ikangtai.android.shecaresdk.ShecareSdk;

/**
 * Created by jerry on 2018/3/20.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShecareSdk.init(this, "123456", "1");
    }
}
