package by.andersen.intern.dobrov.mynewsapi.domain;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

public interface NewsListCallback {

    void setNews(List<Article> newArticles);

    void setIsInternet(boolean noInternet);

}
