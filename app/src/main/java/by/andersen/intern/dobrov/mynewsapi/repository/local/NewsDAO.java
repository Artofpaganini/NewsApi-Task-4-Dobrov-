package by.andersen.intern.dobrov.mynewsapi.repository.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.annotation.NonNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.model.Article;

@Dao
public interface NewsDAO {

    @Query("SELECT * FROM articles")
    List<Article> getAllNewsArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNewsArticles(@NonNull List<Article> articles);

    @Query("DELETE FROM articles")
    void deleteAllNews();

    @Query("SELECT COUNT(*) FROM articles")
    int getCountRows();

}
