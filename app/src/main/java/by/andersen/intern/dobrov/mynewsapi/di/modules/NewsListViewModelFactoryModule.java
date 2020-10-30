package by.andersen.intern.dobrov.mynewsapi.di.modules;

import by.andersen.intern.dobrov.mynewsapi.di.scope.MyScope;
import by.andersen.intern.dobrov.mynewsapi.presentation.list.viewmodel.NewsListViewModel;
import by.andersen.intern.dobrov.mynewsapi.presentation.list.viewmodel.NewsListViewModelFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class NewsListViewModelFactoryModule {

    @MyScope
    @Provides
    NewsListViewModelFactory provideNewsListViewModel(NewsListViewModel newsListViewModel) {
        return new NewsListViewModelFactory(newsListViewModel);
    }

}
