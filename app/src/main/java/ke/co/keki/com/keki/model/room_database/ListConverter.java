package ke.co.keki.com.keki.model.room_database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ke.co.keki.com.keki.model.pojo.Ingredients;


public class ListConverter {
    @TypeConverter
    public static List<Ingredients> fromString(String value) {
        Type listType = new TypeToken<List<Ingredients>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(List<Ingredients> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
