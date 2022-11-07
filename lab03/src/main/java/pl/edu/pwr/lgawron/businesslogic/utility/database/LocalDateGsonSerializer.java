package pl.edu.pwr.lgawron.businesslogic.utility.database;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// https://github.com/google/gson/issues/1507
// https://stackoverflow.com/questions/70412805/what-does-this-error-mean-java-lang-reflect-inaccessibleobjectexception-unable

public class LocalDateGsonSerializer implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH));
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
