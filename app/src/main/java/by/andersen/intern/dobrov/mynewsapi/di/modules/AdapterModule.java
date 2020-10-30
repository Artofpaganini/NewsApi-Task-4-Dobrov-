package by.andersen.intern.dobrov.mynewsapi.di.modules;


import by.andersen.intern.dobrov.mynewsapi.di.scope.MyScope;
import by.andersen.intern.dobrov.mynewsapi.presentation.list.adapter.RecyclerViewAdapter;
import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

    @MyScope
    @Provides
    static RecyclerViewAdapter provideRecyclerViewAdapter() {
        return new RecyclerViewAdapter();
    }
}
