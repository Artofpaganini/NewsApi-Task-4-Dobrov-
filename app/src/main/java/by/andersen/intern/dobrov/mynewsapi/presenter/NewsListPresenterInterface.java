package by.andersen.intern.dobrov.mynewsapi.presenter;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;

public interface NewsListPresenterInterface {

    void swipe();

    void showError();

    void setData(List<Article> articles);

    void addDate(List<Article> articles);
}
