package pl.edu.pwr.lgawron.resultutility;

import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.PersonEnrolledJugs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultInformationParser {
    public static List<String[]> parseEnrolledMap(Map<PersonEnrolledJugs, List<Integer>> personAssignmentData) {
        List<String[]> stringDataArray = new ArrayList<>();

        for (Map.Entry<PersonEnrolledJugs, List<Integer>> enrolledEntry : personAssignmentData.entrySet()) {

            List<List<Integer>> results = new ArrayList<>();
            for (Map.Entry<Jug, Integer> jugEntry : enrolledEntry.getKey().getEnrolledJugs().entrySet()) {
                results.add(List.of(jugEntry.getKey().getId(), jugEntry.getValue()));
            }

            stringDataArray.add(new String[]{String.valueOf(enrolledEntry.getKey().getPerson().getId()), String.valueOf(results)});
        }

        return stringDataArray;
    }
}
