package pl.edu.pwr.lgawron.businesslogic.utility.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonSerializeUtility<T> {
    private static final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();

    public static <T> String serializeToJson(List<T> listToSerialize) {
        return gson.toJson(listToSerialize);
    }

    public static <T> List<T> serializeFromJson(String stringToDeserialize, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(stringToDeserialize, typeOfT);
    }
}
