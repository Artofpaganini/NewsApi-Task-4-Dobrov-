package by.andersen.intern.dobrov.mynewsapi.repository.api;


import by.andersen.intern.dobrov.mynewsapi.entity.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<News> getNewsByCategory(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );
}
