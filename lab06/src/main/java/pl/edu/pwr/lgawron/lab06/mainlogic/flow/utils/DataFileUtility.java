package pl.edu.pwr.lgawron.lab06.mainlogic.flow.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataFileUtility {
    public static List<String> readFileLines(String filename) {
        try {
            return Files.readAllLines(Path.of(filename));
        } catch (IOException e) {
            System.out.println("ERROR: Could not read data from " + filename);
        }
        return List.of();
    }
}
