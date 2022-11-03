package pl.edu.pwr.lgawron.businesslogic.utility.date;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class DateReader {
    public final String filename;
    private LocalDate now;

    public DateReader(String filename) {
        this.filename = filename;
        this.refreshCurrentDate();
    }

    public void refreshCurrentDate() {
        try {
//            var reader = new BufferedReader(new FileReader(filename));
//            var current = reader.readLine();
//            reader.close();
            String current = Files.readString(Path.of(filename));
            if (current != null) {
                now = LocalDate.parse(current);
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            System.out.println("ERROR: Could not read date from file!");
            now = LocalDate.now();
        }
    }

    public LocalDate getCurrentDate() {
        return now;
    }
}
