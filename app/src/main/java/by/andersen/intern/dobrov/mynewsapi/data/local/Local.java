package by.andersen.intern.dobrov.mynewsapi.data.local;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface Local {

    Observable<List<Article>> getNews();

    Completable insertNewsArticles(@NonNull List<Article> articles);

    Completable deleteAllNewsArticles();

}
