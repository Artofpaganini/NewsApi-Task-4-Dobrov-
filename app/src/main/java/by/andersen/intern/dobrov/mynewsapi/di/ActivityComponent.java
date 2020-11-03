package by.andersen.intern.dobrov.mynewsapi.di;


import android.app.Activity;

import by.andersen.intern.dobrov.mynewsapi.di.modules.AdapterModule;
import by.andersen.intern.dobrov.mynewsapi.di.modules.LocalModule;
import by.andersen.intern.dobrov.mynewsapi.di.modules.NewsListViewModelFactoryModule;
import by.andersen.intern.dobrov.mynewsapi.di.modules.RemoteModule;
import by.andersen.intern.dobrov.mynewsapi.di.modules.DataInterfaceModule;
import by.andersen.intern.dobrov.mynewsapi.di.scope.MyScope;
import by.andersen.intern.dobrov.mynewsapi.presentation.list.NewsListActivity;
import dagger.BindsInstance;
import dagger.Component;


@MyScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                NewsListViewModelFactoryModule.class,
                DataInterfaceModule.class,
                RemoteModule.class,
                LocalModule.class,
                AdapterModule.class
        }
)
public interface ActivityComponent {


    @Component.Builder
    interface Builder {

        @BindsInstance
        ActivityComponent.Builder activity(Activity activity);

        ActivityComponent.Builder appComponent(AppComponent appComponent);

        ActivityComponent build();

    }

    void inject(NewsListActivity newsListActivity);

}
