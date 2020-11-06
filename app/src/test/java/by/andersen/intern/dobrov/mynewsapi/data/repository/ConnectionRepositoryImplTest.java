package by.andersen.intern.dobrov.mynewsapi.data.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.data.local.Local;
import by.andersen.intern.dobrov.mynewsapi.data.remote.Remote;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Source;
import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ConnectionRepositoryImplTest {

    private String keyword = "Keyword";

    private Article article1 = new Article(new Source(), "Some Author", "Some title with  length of symbol  more than" +
            " 50 symbol and we need to add FILTERED and change to Date Format", "Some description", "Some url", "Some image",
            "2020-10-12T06:19:34Z", "Some content");
    private Article article2 = new Article(new Source(), "Some Author", "Some title",
            "Some description", "Some url", "Some image", "Some pub;ishAt",
            "Some content");

    public List<Article> articles = new ArrayList<>();
    private ConnectionRepositoryImpl connectionRepository;

    @Mock
    private Local local;

    @Mock
    private Remote remote;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        connectionRepository = new ConnectionRepositoryImpl(remote, local);

    }

    @Test
    public void test_ShouldCheckRemoteAndLocalOnNull() {
        Assert.assertNotNull(remote);
        Assert.assertNotNull(local);
    }

    @Test
    public void test_ShouldCheckObserverTrigger() {

        articles.add(article1);
        articles.add(article2);

        final Observable<List<Article>> returnedArticlesList = Observable.just(articles);

        when(remote.requestNews(keyword)).thenReturn(returnedArticlesList);

        connectionRepository.loadNews(keyword);

        verify(remote).requestNews(keyword);

        verifyNoMoreInteractions(remote);

    }
}