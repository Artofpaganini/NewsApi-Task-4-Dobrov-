package by.andersen.intern.dobrov.mynewsapi.data.local;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class LocalDataImpl implements Local {

    private static final String TAG = "LocalData";
    private final NewsDatabase newsDatabase;

    @Inject
    public LocalDataImpl(NewsDatabase newsDatabase) {
        this.newsDatabase = newsDatabase;
        Log.d(TAG, "LocalData: START TO INIT DATA BASE");

    }

    @Override
    public Observable<List<Article>> getNews() {
        Log.d(TAG, "getNews: GET DATA ");
        return newsDatabase.newsDAO().getAllNewsArticles();

    }

    @Override
    public Completable insertNewsArticles(@NonNull List<Article> articles) {
        return newsDatabase.newsDAO().insertAllNewsArticles(articles);

    }

    @Override
    public Completable deleteAllNewsArticles() {
        Log.d(TAG, "deleteAllNewsArticles: DELETE DATA ");
        return newsDatabase.newsDAO().deleteAllNews();
    }

}
