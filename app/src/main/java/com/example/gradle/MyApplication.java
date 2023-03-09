package com.example.gradle;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String key = getResources().getString(R.string.yandex_maps_key);
        MapKitFactory.setApiKey(key);
    }

}
