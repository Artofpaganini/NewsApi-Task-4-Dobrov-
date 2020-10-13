package by.andersen.intern.dobrov.mynewsapi.repository.local;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.model.Article;
import by.andersen.intern.dobrov.mynewsapi.repository.ConnectionRepository;


public class LocalData {

    private static final String TAG = "LocalData";
    private static final int TIMER = 60000;
    private final NewsDatabase newsDatabase;
    private final ConnectionRepository connectionRepository;

    private int rowsCounter;

    public LocalData(Application application, ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
        newsDatabase = NewsDatabase.getInstance(application);

        Log.d(TAG, "LocalData: START TO INIT DATA BASE");

    }

    public NewsDatabase getNewsDatabase() {
        return newsDatabase;
    }

    //получение всех данных  из бд
    public void getNews() {
        new GetAllNewsTask(this).execute();
    }

    @SuppressWarnings("unchecked")
    //добавление списка данных в бд
    public void insertNewsArticles(@NonNull List<Article> articles) {

        new InsertNewsArticlesTask(this).execute(articles);
    }

    //удаление всех данных
    public void deleteAllNewsArticles() {

        Log.d(TAG, "deleteAllNewsArticles: DELETE NEWS FROM DB");
        new DeleteNewsArticlesTask(this).execute();
    }

    //количество заполненных полей в таблице
    public void getCountRows() {
        new GetCountRows(this, rowsCounter).execute();
    }

    public int getRowsCounter() {
        return rowsCounter;
    }

    protected static class GetAllNewsTask extends AsyncTask<Void, Void, List<Article>> {
        private final LocalData localData;

        public GetAllNewsTask(LocalData localData) {
            this.localData = localData;
        }

        @Override
        protected List<Article> doInBackground(Void... voids) {

            return localData.getNewsDatabase().newsDAO().getAllNewsArticles();

        }

        @Override
        protected void onPostExecute(@NonNull List<Article> articles) {

            localData.connectionRepository.getNewsListCallback().setNews(articles);

            Log.d(TAG, "onPostExecute: GET ALL NEWS TO LOCAL DB");
        }
    }

    protected static class InsertNewsArticlesTask extends AsyncTask<List<Article>, Void, Void> {
        private final LocalData localData;

        public InsertNewsArticlesTask(LocalData localData) {
            this.localData = localData;
        }

        //всегда будет передаваться только 1 аргумент
        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Article>... lists) {

            if (lists != null && lists.length > 0) {
                localData.getNewsDatabase().newsDAO().insertAllNewsArticles(lists[0]);

                Log.d(TAG, "InsertNewsArticlesTask: INSERT NEWS TO LOCAL DB");
            }
            return null;
        }
    }

    //завязать  все данные на бд и чтобы бд постоянно обновлялась
    protected static class DeleteNewsArticlesTask extends AsyncTask<Void, Void, Void> {
        private final LocalData localData;

        public DeleteNewsArticlesTask(LocalData localData) {
            this.localData = localData;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            new Thread(() -> {
                try {
                    Log.d(TAG, "setArticles: START SAVE DATA  IN THE DB - 1 MIN");
                    Thread.sleep(TIMER);
                    localData.getNewsDatabase().newsDAO().deleteAllNews();
                    Log.d(TAG, "setArticles: DELETED DATA FROM DB AFTER 1 MIN");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();

            return null;
        }
    }

    protected static class GetCountRows extends AsyncTask<Void, Void, Integer> {
        private final LocalData localData;
        private int counter = 0;

        public GetCountRows(LocalData localData, int counter) {
            this.localData = localData;
            this.counter = counter;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return localData.getNewsDatabase().newsDAO().getCountRows();

        }

        @Override
        protected void onPostExecute(Integer integer) {
            counter = integer;
        }
    }

}
