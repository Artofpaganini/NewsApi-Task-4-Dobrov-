package by.andersen.intern.dobrov.mynewsapi.activity;

import android.content.Intent;


import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import by.andersen.intern.dobrov.mynewsapi.entity.Article;
import by.andersen.intern.dobrov.mynewsapi.R;

public class NewsDetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private TextView titleToolbar;
    private TextView subtitleToolbar;
    private TextView newsTitle;
    private TextView description;
    private Toolbar toolbar;
    private ImageView glideImageView;
    private Article article;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleToolbar = findViewById(R.id.title_toolbar);
        subtitleToolbar = findViewById(R.id.subtitle_toolbar);

        newsTitle = findViewById(R.id.title);
        description = findViewById(R.id.desc);

        glideImageView = findViewById(R.id.glide_image);

        showData();

    }

    private void showData() {

        Intent intent = getIntent();
        article = intent.getParcelableExtra(Article.class.getSimpleName());

        titleToolbar.setText(article.getSource().getName());
        subtitleToolbar.setText(article.getUrl());
        newsTitle.setText(article.getTitle());
        description.setText(article.getDescription());

        RequestOptions requestOptions = new RequestOptions();

        Glide
                .with(this)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .into(glideImageView);

    }
}
