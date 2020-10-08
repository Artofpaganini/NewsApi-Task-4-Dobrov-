package by.andersen.intern.dobrov.mynewsapi.activity;


import android.support.annotation.NonNull;

import android.support.v4.widget.SwipeRefreshLayout;
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


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import by.andersen.intern.dobrov.mynewsapi.R;
import by.andersen.intern.dobrov.mynewsapi.adapter.MyAdapter;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;
import by.andersen.intern.dobrov.mynewsapi.presenter.NewsListPresenter;
import by.andersen.intern.dobrov.mynewsapi.view.NewsListView;


public class NewsListActivity extends MvpAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, NewsListView {
    private static final String TAG = "MainActivity";

    private static final String NETWORK_ERROR = "Sorry, Network failure, Please Try Again";

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private TextView titleToolbar;
    private Spinner spinner;

    private String selectedCategory;

    @InjectPresenter
    NewsListPresenter newsListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        spinner = findViewById(R.id.spinner);
        titleToolbar = findViewById(R.id.toolbar_title_main);

        myAdapter = new MyAdapter();

        newsListPresenter.loadData(selectedCategory);

        setSupportActionBar(toolbar);
        adapterSpinner();

        Objects.requireNonNull(getSupportActionBar()).setTitle(selectedCategory);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        getCategoryFromSpinner();

    }

    //вывод ошибки
    @Override
    public void showError() {
        Log.d(TAG, "showError: SENT ERROR MESSAGE ABOUT CONNECTION  NETWORK");
        Toast.makeText(NewsListActivity.this, NETWORK_ERROR, Toast.LENGTH_SHORT).show();
        System.out.println("123");

    }

    //загрузка данных
    @Override
    public void showData(List<Article> articles) {

        myAdapter.setArticles(articles);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);

    }

    //обновление при запуске или смене категории
    @Override
    public void swipe() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        onLoadingSwipeRefresh(selectedCategory);
        Log.d(TAG, "onRefresh: RELOAD PAGE BY Category " + selectedCategory);
    }

    private void onLoadingSwipeRefresh(@NonNull final String keyword) {

        swipeRefreshLayout.post(() -> newsListPresenter.loadData(keyword)
        );

    }

    //адаптер для спинера
    private void adapterSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.news_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //выполученние выбранной категории из спинера
    private void getCategoryFromSpinner() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
