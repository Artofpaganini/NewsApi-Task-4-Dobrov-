package by.andersen.intern.dobrov.mynewsapi.data.util;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestParameters {

    public RequestParameters() {

    }

    @NonNull
    public static String dateFormat(@NonNull String oldStringDate) {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldStringDate;
        }

        return newDate;
    }

    @NonNull
    public static String getCountry() {
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getLanguage());
        return country.toLowerCase();
    }
}