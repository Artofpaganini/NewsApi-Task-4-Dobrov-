package by.andersen.intern.dobrov.mynewsapi.activity.viewmodel;

import android.app.Application;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.model.Article;
import by.andersen.intern.dobrov.mynewsapi.model.Source;

@RunWith(AndroidJUnit4.class)
@Config(manifest = Config.NONE)
public class NewsListViewModelTest {

    private NewsListViewModel newsListViewModel;

    private static final String KEYWORD = "Keyword";

    @Mock
    private Application application;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        newsListViewModel = new NewsListViewModel(application);

    }

    @Test(expected = NullPointerException.class)
    public void test_ShouldGetNews() {

        List<Article> articleList = new ArrayList<>();
        Article article1 = new Article(new Source(), "111", "111", "111", "111", "111", "111", "111");
        Article article2 = new Article(new Source(), "123", "123", "123", "123", "123", "123", "123");
        Article article3 = new Article(new Source(), "333", "333", "333", "333", "333", "333", "333");

        articleList.add(article1);
        articleList.add(article2);
        articleList.add(article3);

        newsListViewModel.setNews(articleList);

        Assert.assertEquals(3,newsListViewModel.getRequestArticles(KEYWORD).getValue().size());
    }

    @Test
    public void test_ShouldGetInfoAboutConnectionInternet() {

        boolean isInternet = false;
        newsListViewModel.setIsInternet(isInternet);

        Assert.assertFalse(newsListViewModel.getIsInternet().getValue());

    }

}