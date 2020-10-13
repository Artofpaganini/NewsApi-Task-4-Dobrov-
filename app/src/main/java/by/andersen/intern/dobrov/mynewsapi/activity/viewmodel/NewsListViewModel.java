package by.andersen.intern.dobrov.mynewsapi.activity.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.model.Article;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepository;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepositoryInterface;

public class NewsListViewModel extends AndroidViewModel implements NewsListCallback {
    private static final String TAG = "NewsListViewModel";

    private final MutableLiveData<List<Article>> requestArticles = new MutableLiveData<>();
    private final ConnectionRepositoryInterface connectionRepository;
    public SingleLiveEventForInternet<Boolean> isInternet = new SingleLiveEventForInternet<>();

    private String intermediateKeyword;

    public NewsListViewModel(@NonNull Application application) {
        super(application);
        this.connectionRepository = new ConnectionRepository(application, this);
        Log.d(TAG, "NewsListViewModel: INIT REPOSITORY IN VM ");

    }

    public LiveData<List<Article>> getRequestArticles(@NonNull String keyword) {

        if (requestArticles == null || !(keyword.equals(intermediateKeyword))) {
            intermediateKeyword = keyword;
            connectionRepository.loadNews(keyword);

            Log.d(TAG, "getRequestArticlesForView: LOAD NEWS FIRSTLY");
        }
        Log.d(TAG, "getOldData: LOAD OLD DATA VERSION");

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
        isInternet.setValue(internet);

        Log.d(TAG, "setError: GETTING ERROR FROM CALLBACK");
    }

}


