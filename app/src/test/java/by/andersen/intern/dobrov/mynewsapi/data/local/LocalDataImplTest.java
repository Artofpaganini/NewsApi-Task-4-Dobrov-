package by.andersen.intern.dobrov.mynewsapi.data.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.data.repository.ConnectionRepositoryImpl;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Source;


@RunWith(AndroidJUnit4.class)
@Config(manifest = Config.NONE)
public class LocalDataImplTest {

    private LocalDataImpl localDataImpl;

    @Mock
    private ConnectionRepositoryImpl connectionRepositoryImpl;

    @Mock
    private NewsDAO newsDAO;

    @Mock
    private NewsDatabase database;

    @Mock
    private LocalDataImpl.GetAllNewsTask getAllNewsTask;

    @Mock
    private LocalDataImpl.InsertNewsArticlesTask insertNewsArticlesTask;

    @Mock
    private LocalDataImpl.DeleteNewsArticlesTask deleteNewsArticlesTask;

    @Mock
    private LocalDataImpl.GetCountRows getCountRows;

    @Mock
    private Application application;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, NewsDatabase.class).build();
        newsDAO = database.newsDAO();

        localDataImpl = new LocalDataImpl(application, connectionRepositoryImpl);

        deleteNewsArticlesTask = new LocalDataImpl.DeleteNewsArticlesTask(localDataImpl);
        insertNewsArticlesTask = new LocalDataImpl.InsertNewsArticlesTask(localDataImpl);
        getAllNewsTask = new LocalDataImpl.GetAllNewsTask(localDataImpl);
        getCountRows = new LocalDataImpl.GetCountRows(localDataImpl, localDataImpl.getRowsCounter());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void test_ShouldGetDatabase() {
        Assert.assertNotNull(localDataImpl.getNewsDatabase());
    }

    @Test
    public void test_ShouldGetNews() {
        new Thread(() -> {
            List<Article> articles = new ArrayList<>(getAllNewsTask.doInBackground());
            Assert.assertTrue(articles.size() > 0);
        }).start();

    }

    @Test
    public void test_ShouldInsertNewsArticles() {
        List<Article> articles = Collections.singletonList(new Article(new Source(), "123", "123"
                , "123", "123", "123", "123", "123"));

        new Thread(() -> {
            insertNewsArticlesTask.doInBackground(articles);
            getCountRows.doInBackground();
            getCountRows.onPostExecute(getCountRows.doInBackground());
            Assert.assertEquals(1, localDataImpl.getRowsCounter());
        }).start();


    }

    @Test
    public void test_ShouldDeleteAllNewsArticles() {

        new Thread(() -> {
            deleteNewsArticlesTask.doInBackground();
            getCountRows.doInBackground();
            getCountRows.onPostExecute(getCountRows.doInBackground());
            Assert.assertEquals(0, localDataImpl.getRowsCounter());
        }).start();
    }

}