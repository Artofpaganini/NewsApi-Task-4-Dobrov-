package by.andersen.intern.dobrov.mynewsapi.presentation.list.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewsListViewModelFactory implements ViewModelProvider.Factory {

    NewsListViewModel newsListViewModel;

    public NewsListViewModelFactory(NewsListViewModel newsListViewModel) {
        this.newsListViewModel = newsListViewModel;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) newsListViewModel;
    }
}
