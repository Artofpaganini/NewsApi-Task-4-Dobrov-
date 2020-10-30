package by.andersen.intern.dobrov.mynewsapi.data.remote;

import androidx.annotation.NonNull;

import by.andersen.intern.dobrov.mynewsapi.data.repository.ConnectionRepositoryRemoteCallback;

public interface Remote {

    void requestNews(@NonNull String keyword);

    void setConnectionRepositoryRemoteCallback(ConnectionRepositoryRemoteCallback connectionRepositoryRemoteCallback);
}
