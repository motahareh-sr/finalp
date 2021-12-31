package com.android.myfinalproject.app;

import android.app.Application;
import android.content.Context;

import com.android.myfinalproject.models.Translate;

public class G extends Application {
    public static Context context;
    public static Translate translateModel;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
}
