package by.andersen.intern.dobrov.mynewsapi.data.local;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface NewsDAO {

    @Query("SELECT * FROM articles")
    Observable<List<Article>> getAllNewsArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllNewsArticles(@NonNull List<Article> articles);

    @Query("DELETE FROM articles")
    Completable deleteAllNews();

}
