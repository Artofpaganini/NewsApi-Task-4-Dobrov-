package by.andersen.intern.dobrov.mynewsapi.repository.remote;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.model.Article;
import by.andersen.intern.dobrov.mynewsapi.model.Source;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepositoryCallback;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@Config(manifest = Config.NONE)
public class RemoteDataTest {

    public static final String API_KEY = "59f165be02ad40e2ba19b7347c289ad0";
    public static final String SORT_BY = "publishedAt";
    public static final String KEYWORD = "Keyword";
    public static final String LANGUAGE = "ru";

    public static final String DATE_BEFORE = "2020-10-12T06:19:34Z";
    public static final String DATE_AFTER = "Mon, 12 Oct 2020";

    private RemoteData remoteData;

    @Mock
    private ConnectionRepositoryCallback connectionRepositoryCallback;

    @Mock
    private ApiInterface apiInterface;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        remoteData = new RemoteData(connectionRepositoryCallback);

    }

    @Test
    public void test_ShouldRequestNews() {

        remoteData.requestNews(KEYWORD);

        verify(apiInterface, times(1)).getNewsByCategory(KEYWORD, LANGUAGE, SORT_BY, API_KEY);

    }

    @Test
    public void test_ShouldFormattedData() {

        List<Article> articles = Collections.singletonList(new Article(new Source(), " ", " "
                , " ", " ", " ", DATE_BEFORE, " "));

        remoteData.formatNewsDate(articles);
        Assert.assertEquals(articles.get(0).getPublishedAt(), DATE_AFTER);
    }

}