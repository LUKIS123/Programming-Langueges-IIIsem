package pl.edu.pwr.lgawron.flow.tools;

import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.PersonEnrolledJugs;
import pl.edu.pwr.lgawron.repositories.EnrolledJugsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultPrinter {
    private final EnrolledJugsRepository enrolledJugsRepository;

    public ResultPrinter(EnrolledJugsRepository enrolledJugsRepository) {
        this.enrolledJugsRepository = enrolledJugsRepository;
    }

    public void print() {
        System.out.println("Best match: ");
        System.out.println(enrolledJugsRepository.getBestResult() + "\n");


        System.out.println("Overall Satisfaction: " + enrolledJugsRepository.getOverallSatisfactionParams(false, true) +
                ", Overall Dissatisfaction: " + enrolledJugsRepository.getOverallSatisfactionParams(false, false) + "\n");
        System.out.println("Satisfaction to Dissatisfaction ratio: " + enrolledJugsRepository.ratio + "\n");


        System.out.println("Table form:");
        System.out.println("PersonId; List of matched Jugs (JugId, Volume)");
        for (Map.Entry<PersonEnrolledJugs, List<Integer>> enrolledEntry : enrolledJugsRepository.getBestResult().entrySet()) {
            List<List<Integer>> results = new ArrayList<>();
            for (Map.Entry<Jug, Integer> jugEntry : enrolledEntry.getKey().getEnrolledJugs().entrySet()) {
                results.add(List.of(jugEntry.getKey().getId(), jugEntry.getValue()));
            }
            System.out.println(enrolledEntry.getKey().getPerson().getId() + ";" + results);
        }

//        List<ResultModel> list = new ArrayList<>();
//        for (Map.Entry<PersonEnrolledJugs, List<Integer>> enrolledEntry : enrolledJugsRepository.getBestResult().entrySet()) {
//            ResultModel resultModel = new ResultModel();
//            List<List<Integer>> results = new ArrayList<>();
//            for (Map.Entry<Jug, Integer> jugEntry : enrolledEntry.getKey().getEnrolledJugs().entrySet()) {
//                results.add(List.of(jugEntry.getKey().getId(), jugEntry.getValue()));
//            }
//            resultModel.writeValue(enrolledEntry.getKey().getPerson().getId(), results);
//            list.add(resultModel);
//        }

    }
}
