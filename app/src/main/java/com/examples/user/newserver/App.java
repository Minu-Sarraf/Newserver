package com.examples.user.newserver;

import android.app.Application;
import android.content.Context;

import com.examples.user.newserver.Table2.Listner2;

/**
 * Created by User on 3/20/2016.
 */
public class App extends Application {

    static Context mContext;
@Override
    public void onCreate() {
    super.onCreate();
    this.mContext = getApplicationContext();
    Httplistener.listenerbtn();
    Listner2.listener();

   // Httplistener.listenertop();

    }

    public static Context getContext(){
        return mContext;
    }
}
