package by.andersen.intern.dobrov.mynewsapi.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import by.andersen.intern.dobrov.mynewsapi.data.local.NewsDAO;
import by.andersen.intern.dobrov.mynewsapi.data.local.NewsDatabase;
import by.andersen.intern.dobrov.mynewsapi.di.scope.MyScope;
import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {

    @MyScope
    @Provides
    static NewsDatabase provideGetInstance(Application application) {
        return NewsDatabase.getInstance(application);
    }

    @MyScope
    @Provides
    static NewsDAO provideNewsDAO(NewsDatabase newsDatabase){
        return newsDatabase.newsDAO();
    }

}
