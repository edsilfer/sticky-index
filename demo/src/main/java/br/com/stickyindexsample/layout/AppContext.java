package br.com.stickyindexsample.layout;

import android.app.Application;
import android.content.Context;

public class AppContext extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        AppContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AppContext.context;
    }
}