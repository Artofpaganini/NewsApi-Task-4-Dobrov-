package by.andersen.intern.dobrov.mynewsapi.presentation.list.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.domain.ArticleMapperToViewModel;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class NewsListViewModel extends ViewModel {
    private static final String TAG = "NewsListViewModel";

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<Article>> requestArticles = new MutableLiveData<>();
    private final ArticleMapperToViewModel articleMapperToViewModel;
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private String intermediateKeyword;

    @Inject
    public NewsListViewModel(ArticleMapperToViewModel articleMapperToViewModel) {
        this.articleMapperToViewModel = articleMapperToViewModel;

    }

    public LiveData<List<Article>> getRequestArticles(@NonNull String keyword) {

        if (requestArticles == null || !(keyword.equals(intermediateKeyword))) {
            intermediateKeyword = keyword;
            getNews(keyword);
        }

        return requestArticles;
    }

    public void getNews(@NonNull String keyword) {
        disposables.add(articleMapperToViewModel.getFilteredNewsArticles(keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(value -> error.setValue(value))
                .subscribe(requestArticles::setValue));

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

}


