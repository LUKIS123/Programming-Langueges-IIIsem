package pl.edu.pwr.lgawron.lab07.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataFileUtility {
    public static String readFile(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            System.out.println("ERROR: Could not read data from " + filename);
        }
        return "";
    }

    public static void writeString(String s, String filename) throws RuntimeException {
        try {
            Files.write(Path.of(filename), s.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Could not write data to " + filename);
        }
    }
}