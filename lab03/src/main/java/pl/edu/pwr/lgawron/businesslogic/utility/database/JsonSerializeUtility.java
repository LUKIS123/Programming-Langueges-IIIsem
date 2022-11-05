package pl.edu.pwr.lgawron.businesslogic.utility.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

// https://stackoverflow.com/questions/5554217/deserialize-a-listt-object-with-gson
// https://nipafx.dev/java-modules-optional-dependencies/

public class JsonSerializeUtility<T> {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).setPrettyPrinting().create();

    public static <T> String serializeToJson(List<T> listToSerialize) {
        return gson.toJson(listToSerialize);
    }

    public static <T> List<T> serializeFromJson(String stringToDeserialize, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(stringToDeserialize, typeOfT);
    }
}
