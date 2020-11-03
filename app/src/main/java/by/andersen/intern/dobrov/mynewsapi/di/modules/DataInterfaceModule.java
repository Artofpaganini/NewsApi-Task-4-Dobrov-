package by.andersen.intern.dobrov.mynewsapi.di.modules;

import by.andersen.intern.dobrov.mynewsapi.data.local.Local;
import by.andersen.intern.dobrov.mynewsapi.data.local.LocalDataImpl;
import by.andersen.intern.dobrov.mynewsapi.data.remote.Remote;
import by.andersen.intern.dobrov.mynewsapi.data.remote.RemoteDataImpl;
import by.andersen.intern.dobrov.mynewsapi.data.repository.ConnectionRepositoryImpl;
import by.andersen.intern.dobrov.mynewsapi.di.scope.MyScope;
import by.andersen.intern.dobrov.mynewsapi.domain.ConnectionRepository;
import by.andersen.intern.dobrov.mynewsapi.util.GlobalOnlineCheck;
import by.andersen.intern.dobrov.mynewsapi.util.GlobalOnlineCheckImpl;
import dagger.Binds;
import dagger.Module;

@Module
public interface DataInterfaceModule {

    @MyScope
    @Binds
    ConnectionRepository provideRepository(ConnectionRepositoryImpl connectionRepository);

    @MyScope
    @Binds
    Remote provideRemote(RemoteDataImpl remote);

    @MyScope
    @Binds
    Local provideLocal(LocalDataImpl local);

    @MyScope
    @Binds
    GlobalOnlineCheck provideGlobalOnlineCheck(GlobalOnlineCheckImpl globalOnlineCheck);
}
