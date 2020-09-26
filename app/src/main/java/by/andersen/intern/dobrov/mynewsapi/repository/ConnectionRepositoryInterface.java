package by.andersen.intern.dobrov.mynewsapi.repository;


import android.support.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;

public interface ConnectionRepositoryInterface {

    void loadData(String keyword);

    void connectionWithNewsListPresenter(List<Article> articles);

    List<Article> getArticles();

    void makeRequestCall(@NonNull String keyword);

}
