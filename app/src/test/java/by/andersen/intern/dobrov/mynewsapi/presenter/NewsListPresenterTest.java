package by.andersen.intern.dobrov.mynewsapi.presenter;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;
import by.andersen.intern.dobrov.mynewsapi.entity.Source;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepository;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepositoryInterface;
import by.andersen.intern.dobrov.mynewsapi.view.NewsListView;
import by.andersen.intern.dobrov.mynewsapi.view.NewsListView$$State;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewsListPresenterTest {

    @Mock
    private NewsListView newsListView;

    @Mock
    private NewsListView$$State newsListViewState;

    @Mock
    private ConnectionRepositoryInterface connectionRepository;

    private NewsListPresenter presenter;

    public static final String DATE_BEFORE = "2020-10-12T06:19:34Z";
    public static final String DATE_AFTER = "Пн, 12 окт 2020";


    @Before
    public void setUp() throws Exception {
        presenter = new NewsListPresenter();
        presenter.attachView(newsListView);
        presenter.setViewState(newsListViewState);

        connectionRepository = Mockito.mock(ConnectionRepository.class);

    }

    @Test
    public void test_ShouldLoadDataFromConnectionRepository() {
        presenter = new NewsListPresenter(connectionRepository);
        presenter.loadData(anyString());
        verify(connectionRepository).loadData(anyString());

    }

    @Test
    public void test_ShouldSwipe() {
        presenter.swipe();
        verify(newsListViewState).swipe();

    }

    @Test
    public void test_ShouldShowError() {
        presenter.showError();
        verify(newsListViewState).showError();
    }

    @Test
    public void test_ShouldSetDataFromConnectionRepository() {

        List<Article> articles = connectionRepository.getArticles();
        presenter.setData(articles);
        verify(newsListViewState).showData(articles);

    }

    @Test
    public void test_ShouldAddDate() {

        List<Article> articles = Collections.singletonList(new Article(new Source(), " ", " "
                , " ", " ", " ", DATE_BEFORE, " "));

        presenter.addDate(articles);
        Assert.assertEquals(articles.get(0).getPublishedAt(), DATE_AFTER);

    }

}