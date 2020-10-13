package by.andersen.intern.dobrov.mynewsapi.repository;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.model.Article;

public interface ConnectionRepositoryCallback {

    void setArticles(List<Article> articles);
}
