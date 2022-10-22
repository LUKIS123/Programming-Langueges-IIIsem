package pl.edu.pwr.lgawron.resultutility;

import pl.edu.pwr.lgawron.repositories.EnrolledJugsRepository;

import java.util.List;

public class ResultPrinter {
    private final EnrolledJugsRepository enrolledJugsRepository;

    public ResultPrinter(EnrolledJugsRepository enrolledJugsRepository) {
        this.enrolledJugsRepository = enrolledJugsRepository;
    }

    public void print() {
        System.out.println("Best match: ");
        // System.out.println(enrolledJugsRepository.getBestResult() + "\n");

        System.out.println("Overall Satisfaction: " + enrolledJugsRepository.getOverallSatisfactionParams(false, true) +
                ", Overall Dissatisfaction: " + enrolledJugsRepository.getOverallSatisfactionParams(false, false));
        System.out.println("Satisfaction to Dissatisfaction ratio: " + enrolledJugsRepository.ratio + "\n");

        System.out.println("Table form:");
        System.out.println("PersonId; List of matched Jugs [JugId, VolumeAssigned]");

        List<String[]> strings = ResultInformationParser.parseEnrolledMap(enrolledJugsRepository.getBestResult());
        strings.stream().map(this::convertStringArray).forEach(System.out::println);
        System.out.println("\nNOTE: All matching results were saved in output.csv for You to check out!");
    }

    private String convertStringArray(String[] data) {
        return String.join(";", data);
    }
}
