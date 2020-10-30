package by.andersen.intern.dobrov.mynewsapi.data.remote;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.model.News;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface Remote {

    Observable<List<Article>> requestNews(@NonNull String keyword);

}
