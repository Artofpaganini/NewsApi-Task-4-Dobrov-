package by.andersen.intern.dobrov.mynewsapi.presentation.list.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepository;
import by.andersen.intern.dobrov.mynewsapi.domain.NewsListCallback;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

public class NewsListViewModel extends ViewModel implements NewsListCallback {
    private static final String TAG = "NewsListViewModel";

    private final MutableLiveData<List<Article>> requestArticles = new MutableLiveData<>();
    private final ConnectionRepository connectionRepository;
    public SingleLiveEventForInternet<Boolean> isInternet = new SingleLiveEventForInternet<>();

    private String intermediateKeyword;

    @Inject
    public NewsListViewModel(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
        connectionRepository.setNewsListCallback(this);
        Log.d(TAG, "NewsListViewModel: INIT REPOSITORY IN VM ");
    }

    public LiveData<List<Article>> getRequestArticles(@NonNull String keyword) {

        if (requestArticles == null || !(keyword.equals(intermediateKeyword))) {
            intermediateKeyword = keyword;
            connectionRepository.loadNews(keyword);

            Log.d(TAG, "getRequestArticlesForView: LOAD NEWS FIRSTLY");
        }
        connectionRepository.setNewsListCallback(this);

        return requestArticles;
    }

    public SingleLiveEventForInternet<Boolean> getIsInternet() {
        return isInternet;
    }

    @Override
    public void setNews(List<Article> newArticles) {
        requestArticles.postValue(newArticles);

        Log.d(TAG, "setNews: GETTING CALLBACK FROM REPOSITORY ");
    }

    @Override
    public void setIsInternet(boolean internet) {
        isInternet.postValue(internet);

        Log.d(TAG, "setError: GETTING ERROR FROM CALLBACK");
    }

}


