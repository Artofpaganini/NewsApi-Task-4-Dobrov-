package by.andersen.intern.dobrov.mynewsapi;

import android.app.Application;

import by.andersen.intern.dobrov.mynewsapi.di.AppComponent;
import by.andersen.intern.dobrov.mynewsapi.di.DaggerAppComponent;

public class BaseApp extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this).build();
    }

}

