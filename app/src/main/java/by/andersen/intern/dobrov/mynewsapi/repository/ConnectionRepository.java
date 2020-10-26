package by.andersen.intern.dobrov.mynewsapi.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.activity.viewmodel.NewsListCallback;
import by.andersen.intern.dobrov.mynewsapi.model.Article;
import by.andersen.intern.dobrov.mynewsapi.repository.local.LocalData;
import by.andersen.intern.dobrov.mynewsapi.repository.remote.RemoteData;
import by.andersen.intern.dobrov.mynewsapi.util.RequestParameters;

public class ConnectionRepository implements ConnectionRepositoryInterface, ConnectionRepositoryCallback {

    private static final String TAG = "ConnectionRepository";

    private final RemoteData remoteData;
    private final LocalData localData;
    private final Application application;
    private final NewsListCallback newsListCallback;

    private boolean noInternet;

    public ConnectionRepository(Application application, NewsListCallback newsListCallback) {
        this.application = application;
        this.remoteData = new RemoteData(this);
        this.localData = new LocalData(application, this);
        this.newsListCallback = newsListCallback;
        Log.d(TAG, "ConnectionRepository: INIT REMOTE,LOCAL and CALLBACK  IN REPOSITORY CONSTR");

    }

    @Override
    public void loadNews(@NonNull String keyword) {

        if (RequestParameters.isOnline(application)) {
            remoteData.requestNews(keyword);
            Log.d(TAG, "loadNews: LOAD FROM REMOTE");

        } else {
            noInternet = true;
            localData.getNews();
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

        localData.insertNewsArticles(articles);
        localData.deleteAllNewsArticles();

        Log.d(TAG, "setArticles: INSERT DATA TO DB AFTER 1 MIN");
    }
}


