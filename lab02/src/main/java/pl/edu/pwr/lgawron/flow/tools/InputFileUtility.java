package pl.edu.pwr.lgawron.flow.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class InputFileUtility {
    public List<String> readDataLines(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (NoSuchFileException fileException) {
            System.out.println("ERROR: Please include jug_data.txt and person_data.txt in source folder!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return List.of();
    }
}
