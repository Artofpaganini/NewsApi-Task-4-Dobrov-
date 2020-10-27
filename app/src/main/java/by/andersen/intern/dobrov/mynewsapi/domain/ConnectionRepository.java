package by.andersen.intern.dobrov.mynewsapi.domain;

import androidx.annotation.NonNull;

public interface ConnectionRepository {

    void loadNews(@NonNull String keyword);

}
