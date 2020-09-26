package by.andersen.intern.dobrov.mynewsapi.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepository;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepositoryInterface;
import by.andersen.intern.dobrov.mynewsapi.util.RequestParameters;
import by.andersen.intern.dobrov.mynewsapi.view.NewsListView;


@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> implements NewsListPresenterInterface {
    private static final String TAG = "NewsListPresenter";


    private ConnectionRepositoryInterface connectionInterface;

    public NewsListPresenter() {
        connectionInterface = new ConnectionRepository(this);
    }

    public NewsListPresenter(ConnectionRepositoryInterface connectionInterface) {
        this.connectionInterface = connectionInterface;
    }

    @NonNull
    public void loadData(@NonNull String keyword) {
        connectionInterface.loadData(keyword);
    }


    @Override
    public void addDate(List<Article> articles) {
        Log.d(TAG, "addDate: GET DATE OF EACH ARTICLE");

        for (Article article : articles) {
            article.setPublishedAt(RequestParameters.dateFormat(article.getPublishedAt()));
        }
    }

    @Override
    public void swipe() {
        getViewState().swipe();
    }

    @Override
    public void showError() {
        getViewState().showError();
    }

    @Override
    public void setData(List<Article> articles) {
        getViewState().showData(articles);
    }

}




