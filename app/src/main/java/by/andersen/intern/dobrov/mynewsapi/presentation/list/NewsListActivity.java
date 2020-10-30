package by.andersen.intern.dobrov.mynewsapi.presentation.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import by.andersen.intern.dobrov.mynewsapi.BaseApp;
import by.andersen.intern.dobrov.mynewsapi.R;
import by.andersen.intern.dobrov.mynewsapi.di.ActivityComponent;
import by.andersen.intern.dobrov.mynewsapi.di.DaggerActivityComponent;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;
import by.andersen.intern.dobrov.mynewsapi.presentation.list.adapter.RecyclerViewAdapter;
import by.andersen.intern.dobrov.mynewsapi.presentation.list.viewmodel.NewsListViewModel;
import by.andersen.intern.dobrov.mynewsapi.presentation.list.viewmodel.NewsListViewModelFactory;

public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";

    private NewsListViewModel viewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView titleToolbar;
    private Spinner spinner;
    private String selectedCategory;

    @Inject
    NewsListViewModelFactory newsListViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActivityComponent activityComponent = DaggerActivityComponent
                .builder()
                .appComponent(BaseApp.getAppComponent())
                .activity(this)
                .build();
        activityComponent.inject(this);

        viewModel = new ViewModelProvider(this, newsListViewModelFactory).get(NewsListViewModel.class);

        Log.d(TAG, "onCreate: VIEWMODEL IS " + viewModel.hashCode() + " " + viewModel.toString());

        recyclerViewAdapter = new RecyclerViewAdapter();
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        spinner = findViewById(R.id.spinner);
        titleToolbar = findViewById(R.id.toolbar_title_main);

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

        viewModel.getRequestArticles(keyword).observe(this, this::showData);
    }

    //загрузка данных
    private void showData(@NonNull List<Article> articles) {
        recyclerViewAdapter.setArticles(articles);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

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
