package by.andersen.intern.dobrov.mynewsapi.data.local;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.data.repository.ConnectionRepositoryImpl;


public class LocalDataImpl implements Local {

    private static final String TAG = "LocalData";
    private static final int TIMER = 60000;
    private final NewsDatabase newsDatabase;
    private final ConnectionRepositoryImpl connectionRepositoryImpl;

    private int rowsCounter;

    public LocalDataImpl(Application application, ConnectionRepositoryImpl connectionRepositoryImpl) {
        this.connectionRepositoryImpl = connectionRepositoryImpl;
        newsDatabase = NewsDatabase.getInstance(application);

        Log.d(TAG, "LocalData: START TO INIT DATA BASE");

    }

    public NewsDatabase getNewsDatabase() {
        return newsDatabase;
    }

    //получение всех данных  из бд
    @Override
    public void getNews() {
        new GetAllNewsTask(this).execute();
    }

    @SuppressWarnings("unchecked")
    //добавление списка данных в бд
    @Override
    public void insertNewsArticles(@NonNull List<Article> articles) {

        new InsertNewsArticlesTask(this).execute(articles);
    }

    //удаление всех данных
    @Override
    public void deleteAllNewsArticles() {

        Log.d(TAG, "deleteAllNewsArticles: DELETE NEWS FROM DB");
        new DeleteNewsArticlesTask(this).execute();
    }

    //количество заполненных полей в таблице
    @Override
    public void getCountRows() {
        new GetCountRows(this, rowsCounter).execute();
    }

    public int getRowsCounter() {
        return rowsCounter;
    }

    protected static class GetAllNewsTask extends AsyncTask<Void, Void, List<Article>> {
        private final LocalDataImpl localDataImpl;

        public GetAllNewsTask(LocalDataImpl localDataImpl) {
            this.localDataImpl = localDataImpl;
        }

        @Override
        public List<Article> doInBackground(Void... voids) {

            return localDataImpl.getNewsDatabase().newsDAO().getAllNewsArticles();

        }

        @Override
        protected void onPostExecute(@NonNull List<Article> articles) {

            localDataImpl.connectionRepositoryImpl.getNewsListCallback().setNews(articles);

            Log.d(TAG, "onPostExecute: GET ALL NEWS TO LOCAL DB");
        }
    }

    protected static class InsertNewsArticlesTask extends AsyncTask<List<Article>, Void, Void> {
        private final LocalDataImpl localDataImpl;

        public InsertNewsArticlesTask(LocalDataImpl localDataImpl) {
            this.localDataImpl = localDataImpl;
        }

        //всегда будет передаваться только 1 аргумент
        @SafeVarargs
        @Override
        public final Void doInBackground(List<Article>... lists) {

            if (lists != null && lists.length > 0) {
                localDataImpl.getNewsDatabase().newsDAO().insertAllNewsArticles(lists[0]);

                Log.d(TAG, "InsertNewsArticlesTask: INSERT NEWS TO LOCAL DB");
            }
            return null;
        }
    }

    //завязать  все данные на бд и чтобы бд постоянно обновлялась
    protected static class DeleteNewsArticlesTask extends AsyncTask<Void, Void, Void> {
        private final LocalDataImpl localDataImpl;

        public DeleteNewsArticlesTask(LocalDataImpl localDataImpl) {
            this.localDataImpl = localDataImpl;
        }

        @Override
        public Void doInBackground(Void... voids) {

            new Thread(() -> {
                try {
                    Log.d(TAG, "setArticles: START SAVE DATA  IN THE DB - 1 MIN");
                    Thread.sleep(TIMER);
                    localDataImpl.getNewsDatabase().newsDAO().deleteAllNews();
                    Log.d(TAG, "setArticles: DELETED DATA FROM DB AFTER 1 MIN");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();

            return null;
        }
    }

    protected static class GetCountRows extends AsyncTask<Void, Void, Integer> {
        private final LocalDataImpl localDataImpl;
        private int counter = 0;

        public GetCountRows(LocalDataImpl localDataImpl, int counter) {
            this.localDataImpl = localDataImpl;
            this.counter = counter;
        }

        @Override
        public Integer doInBackground(Void... voids) {
            return localDataImpl.getNewsDatabase().newsDAO().getCountRows();

        }

        @Override
        public void onPostExecute(Integer integer) {
            counter = integer;
        }
    }

}
