package by.andersen.intern.dobrov.mynewsapi.data.local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    private static final String DB_NAME = "news.db";
    private static NewsDatabase newsDatabase;
    private static final Object LOCK = new Object();

    public static NewsDatabase getInstance(Context context) {

        synchronized (LOCK) {
            if (newsDatabase == null) {
                newsDatabase = Room.databaseBuilder(context, NewsDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return newsDatabase;
    }

    public abstract NewsDAO newsDAO();
}
