package by.andersen.intern.dobrov.mynewsapi.data.local;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Source;
import io.reactivex.Completable;
import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocalDataImplTest {

    private LocalDataImpl localData;
    private List<Article> articles;
    private Article article1;
    private Article article2;

    @Mock
    private NewsDatabase newsDatabase;

    @Mock
    private NewsDAO newsDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        localData = new LocalDataImpl(newsDatabase);

        article1 = new Article(new Source(), "Some Author", "Some title",
                "Some description", "Some url", "Some image", "Some pub;ishAt",
                "Some content");
        article2 = new Article(new Source(), "Some Author", "Some title",
                "Some description", "Some url", "Some image", "Some pub;ishAt",
                "Some content");

        articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        final Observable<List<Article>> returnedArticlesList = Observable.just(articles);

        when(newsDatabase.newsDAO()).thenReturn(newsDAO);

        when(newsDAO.insertAllNewsArticles(any())).thenReturn(Completable.complete());
        when(newsDAO.getAllNewsArticles()).thenReturn(returnedArticlesList);
        when(newsDAO.deleteAllNews()).thenReturn(Completable.complete());

    }

    @After
    public void tearDown() throws Exception {
        newsDatabase.close();
    }

    public NewsDAO getNewsDao() {
        return newsDatabase.newsDAO();
    }

    @Test
    public void insertNewsArticles() {

        getNewsDao().insertAllNewsArticles(articles).blockingGet();

        Observable<List<Article>> insertedNews = getNewsDao().getAllNewsArticles();

        assertNotNull(insertedNews);

        getNewsDao().deleteAllNews().blockingGet();
    }

    @Test
    public void test_ShouldInsertNews() {

        localData.insertNewsArticles(articles);
        verify(newsDatabase.newsDAO()).insertAllNewsArticles(articles);
    }

    @Test
    public void test_ShouldGetAllNews() {

        localData.getNews();
        verify(newsDatabase.newsDAO()).getAllNewsArticles();
    }

    @Test
    public void test_ShouldDeleteNews() {

        localData.deleteAllNewsArticles();
        verify(newsDatabase.newsDAO()).deleteAllNews();
    }
}