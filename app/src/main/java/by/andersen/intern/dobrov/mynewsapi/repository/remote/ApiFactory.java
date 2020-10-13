package by.andersen.intern.dobrov.mynewsapi.repository.remote;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String TAG = "ApiClient";

    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static Retrofit retrofit;
    private static ApiFactory apiFactory;

    private ApiFactory() {

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d(TAG, "getApiClient: GET RETROFIT CLIENT");

    }

    public static synchronized ApiFactory getInstance() {

        if (apiFactory == null) {
            apiFactory = new ApiFactory();
        }
        return apiFactory;
    }

    public ApiInterface getApiInterface() {
        return retrofit.create(ApiInterface.class);
    }

}
