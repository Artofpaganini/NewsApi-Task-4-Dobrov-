package by.andersen.intern.dobrov.mynewsapi.data.repository;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

public interface ConnectionRepositoryLocalCallback {

    void setArticlesFromLocal(List<Article> articles);
}
