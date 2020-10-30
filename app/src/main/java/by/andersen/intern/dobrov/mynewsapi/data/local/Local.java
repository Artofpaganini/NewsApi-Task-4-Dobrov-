package by.andersen.intern.dobrov.mynewsapi.data.local;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

public interface Local {

    void getNews();

    void insertNewsArticles(@NonNull List<Article> articles);

    void deleteAllNewsArticles();

    void getCountRows();
}
