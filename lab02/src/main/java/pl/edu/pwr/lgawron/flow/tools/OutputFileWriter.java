//package pl.edu.pwr.lgawron.flow.tools;
//
//import com.opencsv.bean.StatefulBeanToCsv;
//import com.opencsv.bean.StatefulBeanToCsvBuilder;
//import com.opencsv.exceptions.CsvDataTypeMismatchException;
//import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
//import pl.edu.pwr.lgawron.models.Jug;
//import pl.edu.pwr.lgawron.models.PersonEnrolledJugs;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//import com.opencsv.bean.ColumnPositionMappingStrategy;
//import pl.edu.pwr.lgawron.models.ResultInformationHolder;
//import pl.edu.pwr.lgawron.repositories.EnrolledJugsRepository;
//
//
//public class OutputFileWriter {
//    final String CSV_LOCATION = "Output.csv";
//    private final EnrolledJugsRepository enrolledJugsRepository;
//
//    public OutputFileWriter(EnrolledJugsRepository enrolledJugsRepository) {
//        this.enrolledJugsRepository = enrolledJugsRepository;
//    }
//
//    public void flush() {
//
//    }
//
//    public void writeResult() {
//        try {
//            FileWriter writer = new FileWriter(CSV_LOCATION, true);
//
//            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
//            mappingStrategy.setType(ResultInformationHolder.class);
//            String[] columns = new String[]{"PersonId", "JugId", "VolumeAssigned"};
//            mappingStrategy.setColumnMapping(columns);
//
//            StatefulBeanToCsvBuilder<EnrolledJugsRepository> builder = new StatefulBeanToCsvBuilder<>(writer);
//            StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
//            List<ResultInformationHolder> resultInformationHolders = mapToList();
//            beanWriter.write(resultInformationHolders);
//            writer.close();
//        } catch (
//                IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    private List<ResultInformationHolder> mapToList() {
//        List<ResultInformationHolder> resultInformationHolders = new ArrayList<>();
//        Map<PersonEnrolledJugs, List<Integer>> currentPersonAssignmentData = enrolledJugsRepository.getCurrentPersonAssignmentData();
//        for (PersonEnrolledJugs enrolledJugs : currentPersonAssignmentData.keySet()) {
//            for (Map.Entry<Jug, Integer> entry : enrolledJugs.getEnrolledJugs().entrySet()) {
//                resultInformationHolders.add(new ResultInformationHolder(enrolledJugs.getPerson().getId(), entry.getKey().getId(), entry.getValue()));
//            }
//        }
//        return resultInformationHolders;
//    }
//
//}
