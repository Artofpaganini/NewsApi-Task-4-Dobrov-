package by.andersen.intern.dobrov.mynewsapi.di.modules;

import by.andersen.intern.dobrov.mynewsapi.data.local.Local;
import by.andersen.intern.dobrov.mynewsapi.data.local.LocalDataImpl;
import by.andersen.intern.dobrov.mynewsapi.data.remote.Remote;
import by.andersen.intern.dobrov.mynewsapi.data.remote.RemoteDataImpl;
import by.andersen.intern.dobrov.mynewsapi.data.repository.ConnectionRepositoryImpl;
import by.andersen.intern.dobrov.mynewsapi.di.scope.MyScope;
import by.andersen.intern.dobrov.mynewsapi.domain.ArticleMapperToViewModel;
import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepositoryToArticlesMapper;
import by.andersen.intern.dobrov.mynewsapi.domain.interactor.ArticlesMapperImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface DefaultInterfaceModule {

    @MyScope
    @Binds
    ArticleMapperToViewModel provideArticleMapper(ArticlesMapperImpl articlesMapperImpl);

    @MyScope
    @Binds
    ConnectionRepositoryToArticlesMapper provideRepository(ConnectionRepositoryImpl connectionRepository);

    @MyScope
    @Binds
    Remote provideRemote(RemoteDataImpl remote);

    @MyScope
    @Binds
    Local provideLocal(LocalDataImpl local);
}
