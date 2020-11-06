package by.andersen.intern.dobrov.mynewsapi.di.modules;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import by.andersen.intern.dobrov.mynewsapi.data.remote.ApiInterface;
import by.andersen.intern.dobrov.mynewsapi.di.scope.MyScope;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RemoteModule {

    public static final String BASE_URL = "https://newsapi.org/v2/";

    @MyScope
    @Provides
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @MyScope
    @Provides
    static ApiInterface provideApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

}
