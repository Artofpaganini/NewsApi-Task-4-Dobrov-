package by.andersen.intern.dobrov.mynewsapi.data.remote;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.data.util.RequestParameters;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.model.News;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class RemoteDataImpl implements Remote {

    private static final String TAG = "Remote";

    public final String API_KEY = "82a1972ff90249ba971cf9b11c215c7a";
    public final String SORT_BY = "publishedAt";

    private ApiInterface apiInterface;

    @Inject
    public RemoteDataImpl(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;

        Log.d(TAG, "RemoteData:  START INIT REMOTE CONST + INIT RETROFIT");

    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<List<Article>> requestNews(@NonNull String keyword) {
        String language = RequestParameters.getLanguage();

        Observable<News> observableArticlesList = apiInterface.getNewsByCategory(keyword, language, SORT_BY, API_KEY);
        Log.d(TAG, "requestNews: START TO REQUEST NEWS FROM WEB");

        return observableArticlesList
                .map(News::getArticle);

    }
}
