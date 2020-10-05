package by.andersen.intern.dobrov.mynewsapi.activity;


import android.content.Intent;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.squareup.picasso.Picasso;


import by.andersen.intern.dobrov.mynewsapi.models.Article;
import by.andersen.intern.dobrov.mynewsapi.R;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private TextView titleToolbar;
    private TextView subtitleToolbar;
    private TextView newsTitle;
    private TextView description;
    private Toolbar toolbar;
    private ImageView glideImageView;

    private ImageView picassoImageView;



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

        picassoImageView = findViewById(R.id.picasso_image);

        getNewsInformation();

    }


    private void getNewsInformation() {

        Intent intent = getIntent();
        final Article article = intent.getParcelableExtra(Article.class.getSimpleName());

        Log.d(TAG, "getNewsInformation: GET SOURCE " + article.getSource().getName());
        Log.d(TAG, "onCreate: GET ALL INFO FROM INTENT ");

        titleToolbar.setText(article.getSource().getName());
        subtitleToolbar.setText(article.getUrl());
        newsTitle.setText(article.getTitle());
        description.setText(article.getDescription());

        RequestOptions requestOptions = new RequestOptions();

        requestOptions.placeholder(R.drawable.ic_android_black_24dp);


        Glide
                .with(this)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .into(glideImageView);


        Picasso
                .with(this)
                .load(article.getUrlToImage())
                .placeholder(R.drawable.ic_android_black_24dp)
                .into(picassoImageView);

    }
}
