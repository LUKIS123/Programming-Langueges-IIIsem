package pl.edu.pwr.lgawron.businesslogic.utility.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// https://www.marcobehler.com/guides/java-files

public class DataFileUtility {
    public static String readFile(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            System.out.println("ERROR: Could not read data from " + filename);
        }
        return "";
    }

    public static void writeJsonString(String jsonString, String filename) {
        try {
            Files.write(Path.of(filename), jsonString.getBytes());
        } catch (IOException e) {
            System.out.println("ERROR: Could not write data to " + filename);
        }
    }
}
