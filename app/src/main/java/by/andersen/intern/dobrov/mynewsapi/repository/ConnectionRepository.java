package by.andersen.intern.dobrov.mynewsapi.repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;
import by.andersen.intern.dobrov.mynewsapi.entity.News;
import by.andersen.intern.dobrov.mynewsapi.presenter.NewsListPresenterInterface;
import by.andersen.intern.dobrov.mynewsapi.repository.api.ApiFactory;
import by.andersen.intern.dobrov.mynewsapi.repository.api.ApiInterface;
import by.andersen.intern.dobrov.mynewsapi.util.RequestParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionRepository implements ConnectionRepositoryInterface {

    private static final String TAG = "ModelConnection";

    public static final String API_KEY = "59f165be02ad40e2ba19b7347c289ad0";
    public static final String SORT_BY = "publishedAt";

    private NewsListPresenterInterface newsListPresenterInterface;

    private ApiFactory apiFactory;
    private ApiInterface apiInterface;
    private Call<News> call;
    private List<Article> articles = new ArrayList<>();

    public ConnectionRepository(NewsListPresenterInterface newsListPresenterInterface) {
        this.newsListPresenterInterface = newsListPresenterInterface;
    }


    @Override
    public void loadData(@NonNull String keyword) {

        newsListPresenterInterface.swipe();

        registerApi();

        makeRequestCall(keyword);

    }

    private void registerApi() {
        apiFactory = ApiFactory.getInstance();
        apiInterface = apiFactory.getApiInterface();
    }

    public void connectionWithNewsListPresenter(List<Article> articles) {
        newsListPresenterInterface.addDate(articles);
        newsListPresenterInterface.setData(articles);
    }

    public void makeRequestCall(@NonNull String keyword) {

        String language = RequestParameters.getLanguage();

        call = apiInterface.getNewsByCategory(keyword, language, SORT_BY, API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    connectionWithNewsListPresenter(articles);

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                newsListPresenterInterface.showError();
            }
        });
    }

    public void setApiFactory(ApiFactory apiFactory) {
        this.apiFactory = apiFactory;
    }

    public void setApiInterface(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public void setCall(Call<News> call) {
        this.call = call;
    }

    public List<Article> getArticles() {
        return articles;
    }
}


