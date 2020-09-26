package by.andersen.intern.dobrov.mynewsapi.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import by.andersen.intern.dobrov.mynewsapi.R;
import by.andersen.intern.dobrov.mynewsapi.adapter.MyAdapter;
import by.andersen.intern.dobrov.mynewsapi.api.ApiClient;
import by.andersen.intern.dobrov.mynewsapi.api.ApiInterface;
import by.andersen.intern.dobrov.mynewsapi.models.Article;
import by.andersen.intern.dobrov.mynewsapi.models.News;
import by.andersen.intern.dobrov.mynewsapi.models.RequestParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";

    public static final String API_KEY = "59f165be02ad40e2ba19b7347c289ad0";
    public static final String SORT_BY = "publishedAt";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private TextView titleToolbar;
    private Spinner spinner;
    private ApiInterface apiInterface;

    private String selectedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        spinner = findViewById(R.id.spinner);
        titleToolbar = findViewById(R.id.toolbar_title_main);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        setSupportActionBar(toolbar);
        adapterSpinner(spinner);

        Objects.requireNonNull(getSupportActionBar()).setTitle(selectedCategory);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        getCategoryFromSpinner();

    }

    private void loadJson(@NonNull String keyword) {

        swipeRefreshLayout.setRefreshing(true);

        String language = RequestParameters.getLanguage();

        Call<News> call = apiInterface.getNewsByCategory(keyword, language, SORT_BY, API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    myAdapter = new MyAdapter(articles);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                    initListener();

                    swipeRefreshLayout.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Sorry, Network failure, Please Try Again", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: GET ERROR MESSAGE ABOUT CONNECTION");
            }
        });

    }

    //инициализация  новости  по клику  на новость в РВ
    private void initListener() {

        myAdapter.setOnItemClickListener((view, position) -> {

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Article article = articles.get(position);
            Log.d(TAG, "onItemClick: GET ARTICLE DATA " + article.getSource().getName());
            intent.putExtra(Article.class.getSimpleName(), article);

            startActivity(intent);

            Log.d(TAG, "onItemClick: FILL all info and send to detail activity");

        });

    }

    @Override
    public void onRefresh() {
        onLoadingSwipeRefresh(selectedCategory);
        Log.d(TAG, "onRefresh: RELOAD PAGE BY Category " + selectedCategory);
    }

    private void onLoadingSwipeRefresh(@NonNull final String keyword) {

        swipeRefreshLayout.post(() -> loadJson(keyword)
        );

    }

    //адаптер для спинера
    private void adapterSpinner(@NonNull Spinner spinner) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.news_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //выполученние выбранной категории из спинера
    private void getCategoryFromSpinner() {

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                List<String> choosenCategory = Arrays.asList(getResources().getStringArray(R.array.news_category));
                selectedCategory = choosenCategory.get(selectedItemPosition);
                titleToolbar.setText(selectedCategory);

                Log.d(TAG, "onItemSelected: GET CATEGORY " + selectedCategory);
                onLoadingSwipeRefresh(selectedCategory);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

}
