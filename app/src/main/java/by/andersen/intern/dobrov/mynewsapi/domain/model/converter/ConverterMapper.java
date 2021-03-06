package by.andersen.intern.dobrov.mynewsapi.domain.model.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Source;


public class ConverterMapper {

    @TypeConverter
    public String fromSource(Source sourceToString) {
        return new Gson().toJson(sourceToString);
    }

    @TypeConverter
    public Source toSource(String stringToSource) {
        Gson gson = new Gson();
        Source source = gson.fromJson(stringToSource, Source.class);
        return source;
    }
}
