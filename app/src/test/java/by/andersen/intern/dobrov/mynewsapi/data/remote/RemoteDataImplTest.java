package by.andersen.intern.dobrov.mynewsapi.data.remote;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.model.News;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Source;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RemoteDataImplTest {

    private RemoteDataImpl remoteData;
    private News news;
    private List<Article> articles;
    private Article article1;
    private Article article2;

    @Mock
    private ApiInterface apiInterface;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        remoteData = new RemoteDataImpl(apiInterface);

        article1 = new Article(new Source(), "Some Author", "Some title",
                "Some description", "Some url", "Some image", "Some pub;ishAt",
                "Some content");
        article2 = new Article(new Source(), "Some Author", "Some title",
                "Some description", "Some url", "Some image", "Some pub;ishAt",
                "Some content");

        articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        news = new News();
        news.setArticle(articles);

        final Observable<News> returnedNews = Observable.just(news);

        when(apiInterface.getNewsByCategory(any(), any(), any(), any())).thenReturn(returnedNews);

    }

    @Test
    public void test_ShouldCheckThatValueCountMoreThanZero() {

        TestObserver<News> testObserver = TestObserver.create();

        String keyword = "keyword";

        remoteData.requestNews(keyword);
        verify(apiInterface).getNewsByCategory(any(), any(), any(), any());

        apiInterface.getNewsByCategory(any(), any(), any(), any()).subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        assertTrue(testObserver.valueCount() > 0);

    }
}