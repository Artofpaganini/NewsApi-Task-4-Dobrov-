package by.andersen.intern.dobrov.mynewsapi.data.local;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

@Dao
public interface NewsDAO {

    @Query("SELECT * FROM articles")
    List<Article> getAllNewsArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNewsArticles(@NonNull List<Article> articles);

    @Query("DELETE FROM articles")
    void deleteAllNews();

}
