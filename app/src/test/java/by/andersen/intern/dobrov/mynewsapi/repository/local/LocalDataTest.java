package by.andersen.intern.dobrov.mynewsapi.repository.local;


import android.app.Application;
import android.content.Context;

import androidx.room.Room;
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

import by.andersen.intern.dobrov.mynewsapi.model.Article;
import by.andersen.intern.dobrov.mynewsapi.model.Source;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepository;


@RunWith(AndroidJUnit4.class)
@Config(manifest = Config.NONE)
public class LocalDataTest {

    private LocalData localData;

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private NewsDAO newsDAO;

    @Mock
    private NewsDatabase database;

    @Mock
    private LocalData.GetAllNewsTask getAllNewsTask;

    @Mock
    private LocalData.InsertNewsArticlesTask insertNewsArticlesTask;

    @Mock
    private LocalData.DeleteNewsArticlesTask deleteNewsArticlesTask;

    @Mock
    private LocalData.GetCountRows getCountRows;

    @Mock
    private Application application;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        database = Room.inMemoryDatabaseBuilder(application.getApplicationContext(), NewsDatabase.class).build();
        newsDAO = database.newsDAO();

        localData = new LocalData(application, connectionRepository);

        deleteNewsArticlesTask = new LocalData.DeleteNewsArticlesTask(localData);
        insertNewsArticlesTask = new LocalData.InsertNewsArticlesTask(localData);
        getAllNewsTask = new LocalData.GetAllNewsTask(localData);
        getCountRows = new LocalData.GetCountRows(localData, localData.getRowsCounter());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void test_ShouldGetDatabase() {
        Assert.assertNotNull(localData.getNewsDatabase());
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
            Assert.assertEquals(1, localData.getRowsCounter());
        }).start();


    }

    @Test
    public void test_ShouldDeleteAllNewsArticles() {

        new Thread(() -> {
            deleteNewsArticlesTask.doInBackground();
            getCountRows.doInBackground();
            getCountRows.onPostExecute(getCountRows.doInBackground());
            Assert.assertEquals(0, localData.getRowsCounter());
        }).start();
    }



}