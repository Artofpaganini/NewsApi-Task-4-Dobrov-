package by.andersen.intern.dobrov.mynewsapi.repository;

import androidx.annotation.NonNull;

import android.support.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;

public interface ConnectionRepositoryInterface {

    void loadNews(@NonNull String keyword);

    void connectionWithNewsListPresenter(List<Article> articles);

    List<Article> getArticles();

    void makeRequestCall(@NonNull String keyword);

}
