package by.andersen.intern.dobrov.mynewsapi.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.data.local.Local;
import by.andersen.intern.dobrov.mynewsapi.data.remote.Remote;
import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepositoryToArticlesMapper;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ConnectionRepositoryImpl implements ConnectionRepositoryToArticlesMapper {

    private static final String TAG = "ConnectionRepository";

    private final Remote remote;
    private final Local local;

    @Inject
    public ConnectionRepositoryImpl( Remote remote, Local local) {
        this.remote = remote;
        this.local = local;

        Log.d(TAG, "ConnectionRepository: INIT REMOTE,LOCAL and CALLBACK  IN REPOSITORY CONSTR");

    }

    @Override
    public Observable<List<Article>> loadNews(@NonNull String keyword) {

        return remote.requestNews(keyword)
                .flatMap((Function<List<Article>, ObservableSource<List<Article>>>) articles -> local.deleteAllNewsArticles()
                        .andThen(local.insertNewsArticles(articles))
                        .andThen(Observable.just(articles)))
                .onErrorResumeNext(throwable -> {
                    return local.getNews()
                            .flatMap((Function<List<Article>, ObservableSource<List<Article>>>) Observable::just);
                });
    }

}

