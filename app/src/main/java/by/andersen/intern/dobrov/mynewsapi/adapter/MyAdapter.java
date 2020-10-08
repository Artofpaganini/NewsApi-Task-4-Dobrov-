package by.andersen.intern.dobrov.mynewsapi.adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.R;
import by.andersen.intern.dobrov.mynewsapi.activity.NewsDetailActivity;
import by.andersen.intern.dobrov.mynewsapi.entity.Article;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";

    private List<Article> articles;

    public static final String TRANSITION_IMAGE = "transitionImage";

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(articles.get(position));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private TextView publishedAt;
        private TextView source;
        private ImageView imageView;
        private Article article;


        private MyViewHolder(View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            imageView = itemView.findViewById(R.id.element_image_view);

            itemView.setOnClickListener(view -> {

                Intent intent = new Intent(view.getContext(), NewsDetailActivity.class);

                int position = MyViewHolder.this.getAdapterPosition();
                Article article = articles.get(position);

                intent.putExtra(Article.class.getSimpleName(), article);

                Pair pair = Pair.create(view, TRANSITION_IMAGE);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) view.getContext(), pair);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getContext().startActivity(intent, optionsCompat.toBundle());
                } else {
                    view.getContext().startActivity(intent);
                }
            });

        }

        private void bind(@NotNull Article article) {

            this.article = article;
            this.title.setText(article.getTitle());
            this.source.setText(article.getSource().getName());
            this.publishedAt.setText(article.getPublishedAt());
            this.author.setText(article.getAuthor());

            RequestOptions requestOptions = new RequestOptions();
            Glide
                    .with(itemView)
                    .load(article.getUrlToImage())
                    .apply(requestOptions)
                    .into(this.imageView);

            Log.d(TAG, "bind: WE GET IMAGE FROM  BIND " + this.imageView);

        }

    }

}