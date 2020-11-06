package by.andersen.intern.dobrov.mynewsapi.domain;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import io.reactivex.Observable;

public interface ConnectionRepositoryToArticlesMapper {

    Observable<List<Article>> loadNews(@NonNull String keyword);

}
