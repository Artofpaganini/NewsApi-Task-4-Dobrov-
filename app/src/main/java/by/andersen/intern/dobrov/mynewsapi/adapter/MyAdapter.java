package by.andersen.intern.dobrov.mynewsapi.adapter;


import android.app.Activity;
import android.content.Context;
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


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.R;

import by.andersen.intern.dobrov.mynewsapi.activity.DetailActivity;

import by.andersen.intern.dobrov.mynewsapi.models.RequestParameters;
import by.andersen.intern.dobrov.mynewsapi.models.Article;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";

    private List<Article> articles;


    public static final String TRANSITION_IMAGE = "transitionImage";

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Article> articles;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(List<Article> articles) {
        this.articles = articles;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new MyViewHolder(view);
    }



        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(articles.get(position));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView author;
        private TextView publishedAt;
        private TextView source;
        private ImageView imageView;
        private Article article;



        private MyViewHolder(View itemView) {

            super(itemView);

        private Article article;

        private OnItemClickListener onItemClickListener;

        private MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);

            imageView = itemView.findViewById(R.id.element_image_view);

            itemView.setOnClickListener(view -> {

                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);

                int position = getAdapterPosition();
                Article article = articles.get(position);

                intent.putExtra(Article.class.getSimpleName(), article);

                Pair pair = Pair.create(imageView, TRANSITION_IMAGE);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) itemView.getContext(), pair);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemView.getContext().startActivity(intent, optionsCompat.toBundle());
                } else {
                    itemView.getContext().startActivity(intent);
                }
            });

            this.onItemClickListener = onItemClickListener;


        }

        private void bind(@NotNull Article article) {

            this.article = article;
            this.title.setText(article.getTitle());
            this.source.setText(article.getSource().getName());
            this.publishedAt.setText(RequestParameters.dateFormat(article.getPublishedAt()));
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

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
