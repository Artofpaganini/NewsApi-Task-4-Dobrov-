package by.andersen.intern.dobrov.mynewsapi.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepositoryToArticlesMapper;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Source;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ArticlesMapperImplTest {

    private String keyword = "Keyword";

    private Article article1 = new Article(new Source(), "Some Author", "Some title with  length of symbol  more than" +
            " 50 symbol and we need to add FILTERED and change to Date Format", "Some description", "Some url", "Some image",
            "2020-10-12T06:19:34Z", "Some content");
    private Article article2 = new Article(new Source(), "Some Author", "Some title",
            "Some description", "Some url", "Some image", "Some pub;ishAt",
            "Some content");

    public static final String DATE_BEFORE = "2020-10-12T06:19:34Z";
    public static final String DATE_AFTER = "Mon, 12 Oct 2020";
    public static final String TITLE_EXPECTED = "Some title with  length of symbol  more than " +
            "50 symbol and we need to add FILTERED and change to Date Format FILTERED";

    private List<Article> articles = new ArrayList<>();

    private ArticlesMapperImpl articlesMapper;

    @Mock
    private ConnectionRepositoryToArticlesMapper connectionRepositoryToArticlesMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        articlesMapper = new ArticlesMapperImpl(connectionRepositoryToArticlesMapper);

        articles.add(article1);
        articles.add(article2);

        final Observable<List<Article>> returnedArticlesList = Observable.just(articles);

        when(connectionRepositoryToArticlesMapper.loadNews(keyword))
                .thenReturn(returnedArticlesList);
    }

    @Test
    public void test_ShouldModifyArticle() {

        article1.setPublishedAt(DATE_BEFORE);
        articles.add(article1);
        articlesMapper.modifyArticle(article1);

        assertEquals(DATE_AFTER, article1.getPublishedAt());
        assertEquals(TITLE_EXPECTED, article1.getTitle());
    }

    @Test
    public void test_ShouldCheckThatNoErrorAndGetCount() {

        TestObserver<List<Article>> testObserver = TestObserver.create();

        connectionRepositoryToArticlesMapper.loadNews(keyword).subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        assertEquals(1, testObserver.valueCount());
    }
}


