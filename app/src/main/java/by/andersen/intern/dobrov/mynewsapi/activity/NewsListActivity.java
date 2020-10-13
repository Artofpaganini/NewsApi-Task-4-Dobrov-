package by.andersen.intern.dobrov.mynewsapi.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import by.andersen.intern.dobrov.mynewsapi.R;
import by.andersen.intern.dobrov.mynewsapi.activity.viewmodel.NewsListViewModel;
import by.andersen.intern.dobrov.mynewsapi.adapter.MyAdapter;
import by.andersen.intern.dobrov.mynewsapi.model.Article;
import by.andersen.intern.dobrov.mynewsapi.util.RequestParameters;

public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";

    private static final String NETWORK_ERROR = "Sorry, Network failure, Please Try Again";

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView titleToolbar;
    private Spinner spinner;
    private String selectedCategory;

    private NewsListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        spinner = findViewById(R.id.spinner);
        titleToolbar = findViewById(R.id.toolbar_title_main);

        myAdapter = new MyAdapter();
        viewModel = ViewModelProviders.of(this).get(NewsListViewModel.class);

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

    private void initViewModel(@NonNull String keyword) {

        if (RequestParameters.isOnline(this)) {
            viewModel.getRequestArticles(keyword).observe(this, NewsListActivity.this::showData);

            Log.d(TAG, "initViewModel: GET  DATA FROM VM ");
        } else {
            viewModel.getRequestArticles(keyword).observe(this, NewsListActivity.this::showData);
            viewModel.getIsInternet().observe(this, aBoolean -> NewsListActivity.this.showError());

        }
    }

    //вывод ошибки
    private void showError() {
        Log.d(TAG, "showError: SENT ERROR MESSAGE ABOUT CONNECTION  NETWORK");
        Toast.makeText(NewsListActivity.this, NETWORK_ERROR, Toast.LENGTH_SHORT).show();

    }

    //загрузка данных
    private void showData(@NonNull List<Article> articles) {
        myAdapter.setArticles(articles);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        onLoadingSwipeRefresh(selectedCategory);
        Log.d(TAG, "onRefresh: RELOAD PAGE BY Category " + selectedCategory);
    }

    private void onLoadingSwipeRefresh(@NonNull final String keyword) {

        swipeRefreshLayout.post(() -> NewsListActivity.this.initViewModel(keyword)
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
                onLoadingSwipeRefresh(selectedCategory);

                Log.d(TAG, "onItemSelected: GET SPINNER CATEGORY " + selectedCategory);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

}
