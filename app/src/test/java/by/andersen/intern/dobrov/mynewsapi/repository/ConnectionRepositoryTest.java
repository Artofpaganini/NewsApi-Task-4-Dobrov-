package by.andersen.intern.dobrov.mynewsapi.repository;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import by.andersen.intern.dobrov.mynewsapi.entity.News;
import by.andersen.intern.dobrov.mynewsapi.presenter.NewsListPresenterInterface;
import by.andersen.intern.dobrov.mynewsapi.repository.api.ApiInterface;
import retrofit2.Call;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionRepositoryTest {


    public static final String API_KEY = "59f165be02ad40e2ba19b7347c289ad0";
    public static final String SORT_BY = "publishedAt";
    public static final String KEYWORD = "Keyword";
    public static final String LANGUAGE = "language";

    private ConnectionRepositoryInterface connectionRepository;

    @Mock
    private NewsListPresenterInterface newsListPresenterInterface;

    @Mock
    private ApiInterface apiInterface;

    private Call<News> mockCall;


    @Before
    public void setUp() throws Exception {
        connectionRepository = new ConnectionRepository(newsListPresenterInterface);
    }

    @Test
    public void test_ShouldReturnCall() {

        connectionRepository.loadData(KEYWORD);
        when(apiInterface.getNewsByCategory(KEYWORD, LANGUAGE, SORT_BY, API_KEY)).thenReturn(mockCall);

    }

    @Test
    public void test_ShouldSwipe() {
        connectionRepository.loadData(KEYWORD);
        verify(newsListPresenterInterface).swipe();

    }

    @Test
    public void test_ShouldAddDateAndSetData() {

        connectionRepository.connectionWithNewsListPresenter(any());

        verify(newsListPresenterInterface).addDate(anyList());
        verify(newsListPresenterInterface).setData(anyList());
    }

}