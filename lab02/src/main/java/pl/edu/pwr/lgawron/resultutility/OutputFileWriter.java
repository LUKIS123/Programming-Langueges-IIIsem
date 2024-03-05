package pl.edu.pwr.lgawron.resultutility;


import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputFileWriter {
    public final String filename = "output.csv";

    public void writeResults(List<String[]> stringList, double satisfactionRatio) {
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            CSVWriter writer = new CSVWriter(fileWriter, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeNext(new String[]{"Match", "Satisfaction:" + satisfactionRatio});
            stringList.forEach(writer::writeNext);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flushCSVFile() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}