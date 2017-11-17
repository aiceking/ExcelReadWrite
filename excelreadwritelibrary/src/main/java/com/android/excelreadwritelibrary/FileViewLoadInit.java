package com.android.excelreadwritelibrary;

import android.content.Context;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by static on 2017/11/17/017.
 */

public class FileViewLoadInit {
    public static void init(Context context){
        QbSdk.initX5Environment(context,null);
    }
}
