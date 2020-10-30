package by.andersen.intern.dobrov.mynewsapi.domain.interactor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.data.util.RequestParameters;
import by.andersen.intern.dobrov.mynewsapi.domain.ArticleMapperToViewModel;
import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepositoryToArticlesMapper;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ArticlesMapperImpl implements ArticleMapperToViewModel {

    private static final String TAG = "ArticlesMapperImpl";

    private final ConnectionRepositoryToArticlesMapper connectionRepositoryToArticlesMapper;
    public final static int TITLE_LENGTH = 50;

    @Inject
    public ArticlesMapperImpl(ConnectionRepositoryToArticlesMapper connectionRepositoryToArticlesMapper) {
        this.connectionRepositoryToArticlesMapper = connectionRepositoryToArticlesMapper;
    }

    @Override
    public Observable<List<Article>> getFilteredNewsArticles(@NonNull String keyword) {
        return connectionRepositoryToArticlesMapper.loadNews(keyword)
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<Article>, ObservableSource<List<Article>>>) articles -> Observable
                        .fromIterable(articles)
                        .filter(article -> article.getTitle().length() > TITLE_LENGTH)
                        .map(article -> {
                            modifyArticle(article);
                            Log.d(TAG, "requestNews: " + article.getAuthor());
                            return article;
                        })
                        .buffer(articles.size()));
    }

    public void modifyArticle(@NonNull Article article) {

        article.setPublishedAt(RequestParameters.dateFormat(article.getPublishedAt()));
        article.setTitle(article.getTitle().concat(" FILTRED"));

    }
}
