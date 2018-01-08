package com.jaja.baidumap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by ${Terry} on 2018/1/8.
 */
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
