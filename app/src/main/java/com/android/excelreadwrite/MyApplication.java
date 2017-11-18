package com.android.excelreadwrite;

import android.app.Application;

/**
 * Created by static on 2017/11/18/018.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FileViewLoadInit.init(getApplicationContext());
    }
}
