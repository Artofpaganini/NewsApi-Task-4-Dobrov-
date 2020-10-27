package by.andersen.intern.dobrov.mynewsapi.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.data.local.Local;
import by.andersen.intern.dobrov.mynewsapi.data.local.LocalDataImpl;
import by.andersen.intern.dobrov.mynewsapi.data.remote.Remote;
import by.andersen.intern.dobrov.mynewsapi.data.remote.RemoteDataImpl;
import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepository;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.NewsListCallback;
import by.andersen.intern.dobrov.mynewsapi.util.GlobalOnlineCheck;

public class ConnectionRepositoryImpl implements ConnectionRepository, ConnectionRepositoryCallback {

    private static final String TAG = "ConnectionRepository";

    private final Remote remote;
    private final Local local;
    private final Application application;
    private final NewsListCallback newsListCallback;

    private boolean noInternet;

    public ConnectionRepositoryImpl(Application application, NewsListCallback newsListCallback) {
        this.application = application;
        this.remote = new RemoteDataImpl(this);
        this.local = new LocalDataImpl(application, this);

        this.newsListCallback = newsListCallback;
        Log.d(TAG, "ConnectionRepository: INIT REMOTE,LOCAL and CALLBACK  IN REPOSITORY CONSTR");

    }

    @Override
    public void loadNews(@NonNull String keyword) {

        if (GlobalOnlineCheck.isOnline(application)) {
            remote.requestNews(keyword);
            Log.d(TAG, "loadNews: LOAD FROM REMOTE");

        } else {
            noInternet = true;
            local.getNews();
            newsListCallback.setIsInternet(noInternet);
            Log.d(TAG, "loadNews: LOAD FROM LOCAL");
        }
    }

    public NewsListCallback getNewsListCallback() {
        return newsListCallback;
    }

    @Override
    public void setArticles(List<Article> articles) {
        newsListCallback.setNews(articles);

        local.insertNewsArticles(articles);
        local.deleteAllNewsArticles();

        Log.d(TAG, "setArticles: INSERT DATA TO DB AFTER 1 MIN");
    }
}