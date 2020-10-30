package by.andersen.intern.dobrov.mynewsapi.presentation.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import by.andersen.intern.dobrov.mynewsapi.R;

import by.andersen.intern.dobrov.mynewsapi.databinding.ActivityNewsDetailBinding;
import by.andersen.intern.dobrov.mynewsapi.domain.model.Article;

public class NewsDetailActivity extends AppCompatActivity {

    private Article article;
    private Toolbar toolbar;

    private ActivityNewsDetailBinding activityNewsDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityNewsDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);

        showData();

    }

    private void showData() {

        Intent intent = getIntent();
        article = intent.getParcelableExtra(Article.class.getSimpleName());
        activityNewsDetailBinding.setArticles(article);

    }

}
