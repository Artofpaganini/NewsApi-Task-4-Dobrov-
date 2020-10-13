package by.andersen.intern.dobrov.mynewsapi.activity.viewmodel;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.model.Article;

public interface NewsListCallback {

    void setNews(List<Article> newArticles);

    void setIsInternet(boolean noInternet);

}
