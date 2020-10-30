package by.andersen.intern.dobrov.mynewsapi.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;


public class GlobalOnlineCheckImpl implements GlobalOnlineCheck {

    private final Application application;

    @Inject
    public GlobalOnlineCheckImpl(Application application) {
        this.application = application;
    }


    @Override
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
