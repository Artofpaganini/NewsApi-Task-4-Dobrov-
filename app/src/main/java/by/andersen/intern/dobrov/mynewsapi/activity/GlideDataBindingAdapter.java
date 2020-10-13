package by.andersen.intern.dobrov.mynewsapi.activity;

import androidx.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideDataBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void showImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();

        Glide
                .with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }
}
