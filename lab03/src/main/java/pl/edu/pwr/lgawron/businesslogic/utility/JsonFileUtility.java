package pl.edu.pwr.lgawron.businesslogic.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// https://stackoverflow.com/questions/5554217/deserialize-a-listt-object-with-gson
// https://nipafx.dev/java-modules-optional-dependencies/

// do wywalenia pozniej
public class JsonFileUtility<T> {
    private final Gson gson;

    public JsonFileUtility() {
        this.gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
    }

    public void save(List<T> listToSave, File file) {
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(listToSave, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR: Could not save data to database file");
        }
    }

    public <T> List<T> load(Class<T> clazz, File file) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            System.out.println("ERROR: Could not load data to database file");
        }
        return List.of();
    }

//    public <T> List<T> read(Class<T> clazz) {
//        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
//        try {
//            return gson.fromJson(Files.readString(Path.of(filename)), typeOfT);
//        } catch (IOException e) {
//            System.out.println("ERROR: Could not load data to database file");
//        }
//        return List.of();
//    }

}
