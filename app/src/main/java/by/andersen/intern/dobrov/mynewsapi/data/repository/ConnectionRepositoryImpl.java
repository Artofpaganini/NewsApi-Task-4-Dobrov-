package by.andersen.intern.dobrov.mynewsapi.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.data.local.Local;
import by.andersen.intern.dobrov.mynewsapi.data.remote.Remote;
import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepository;
import by.andersen.intern.dobrov.mynewsapi.domain.NewsListCallback;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.util.GlobalOnlineCheck;

public class ConnectionRepositoryImpl implements ConnectionRepository, ConnectionRepositoryRemoteCallback, ConnectionRepositoryLocalCallback {

    private static final String TAG = "ConnectionRepository";

    private boolean noInternet;
    private final Remote remote;
    private final Local local;
    private final GlobalOnlineCheck globalOnlineCheck;
    private NewsListCallback newsListCallback;

    @Inject
    public ConnectionRepositoryImpl(GlobalOnlineCheck globalOnlineCheck, Remote remote, Local local) {
        this.globalOnlineCheck = globalOnlineCheck;
        this.remote = remote;
        this.local = local;

        remote.setConnectionRepositoryRemoteCallback(this::setArticlesFromRemote);
        local.setConnectionRepositoryLocalCallback(this::setArticlesFromLocal);

        Log.d(TAG, "ConnectionRepository: INIT REMOTE,LOCAL and CALLBACK  IN REPOSITORY CONSTR");

    }

    @Override
    public void loadNews(@NonNull String keyword) {

        if (globalOnlineCheck.isOnline()) {
            remote.requestNews(keyword);
            Log.d(TAG, "loadNews: LOAD FROM REMOTE");

        } else {
            noInternet = true;
            local.getNews();
            newsListCallback.setIsInternet(noInternet);
            Log.d(TAG, "loadNews: LOAD FROM LOCAL");
        }
    }

    @Override
    public void setNewsListCallback(NewsListCallback newsListCallback) {
        this.newsListCallback = newsListCallback;
    }

    @Override
    public void setArticlesFromRemote(List<Article> articles) {
        newsListCallback.setNews(articles);
        local.insertNewsArticles(articles);
        local.deleteAllNewsArticles();
        Log.d(TAG, "setArticles:  GET DATA FROM REMOTE AND INSERT DATA TO DB AFTER 1 MIN");
    }

    @Override
    public void setArticlesFromLocal(List<Article> articles) {
        newsListCallback.setNews(articles);
        Log.d(TAG, "setArticlesFromLocal: GET DATA FROM DB");
    }
}