package by.andersen.intern.dobrov.mynewsapi.data.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.data.repository.ConnectionRepositoryRemoteCallback;
import by.andersen.intern.dobrov.mynewsapi.data.util.RequestParameters;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataImpl implements Remote {

    private static final String TAG = "Remote";

    public final String API_KEY = "59f165be02ad40e2ba19b7347c289ad0";
    public final String SORT_BY = "publishedAt";

    private ApiInterface apiInterface;
    private ConnectionRepositoryRemoteCallback connectionRepositoryRemoteCallback;

    private List<Article> articles;

    @Inject
    public RemoteDataImpl(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;

        Log.d(TAG, "RemoteData:  START INIT REMOTE CONST + INIT RETROFIT");

    }

    @Override
    public void requestNews(@NonNull String keyword) {
        String language = RequestParameters.getLanguage();

        Call<News> call = apiInterface.getNewsByCategory(keyword, language, SORT_BY, API_KEY);
        Log.d(TAG, "requestNews: START TO REQUEST NEWS FROM WEB");
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getArticle() != null) {

                        articles = response.body().getArticle();
                        formatNewsDate(articles);

                        connectionRepositoryRemoteCallback.setArticlesFromRemote(articles);

                        Log.d(TAG, "onResponse: GOT NEWS FROM WEB");

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
            }
        });
    }

    public void formatNewsDate(@NonNull List<Article> articles) {

        for (Article article : articles) {
            article.setPublishedAt(RequestParameters.dateFormat(article.getPublishedAt()));
        }
        Log.d(TAG, "formatNewsDate: FORMATTED DATE");
    }

    @Override
    public void setConnectionRepositoryRemoteCallback(ConnectionRepositoryRemoteCallback connectionRepositoryRemoteCallback) {
        this.connectionRepositoryRemoteCallback = connectionRepositoryRemoteCallback;
    }
}
