package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.PersonEnrolledJugs;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.resultutility.OutputFileWriter;
import pl.edu.pwr.lgawron.resultutility.ResultInformationParser;

import java.util.*;

public class EnrolledJugsRepository {
    // store enrolledJug and satisfaction & dissatisfaction factors
    private final Map<PersonEnrolledJugs, List<Integer>> currentPersonAssignmentData = new LinkedHashMap<>();
    private final Map<PersonEnrolledJugs, List<Integer>> bestResult = new LinkedHashMap<>();
    // best results satisfaction/dissatisfaction ratio
    public double ratio;
    private final OutputFileWriter outputFileWriter;

    public EnrolledJugsRepository() {
        this.outputFileWriter = new OutputFileWriter();
        outputFileWriter.flushCSVFile();
    }

    public void initRepository(List<Person> personList) {
        personList.forEach(person -> currentPersonAssignmentData.put(new PersonEnrolledJugs(person), List.of(0, 0)));
    }

    public Map<PersonEnrolledJugs, List<Integer>> getBestResult() {
        return bestResult;
    }

    public boolean addPersonAssignmentData(Person person, Jug jug) {
        for (PersonEnrolledJugs PersonEnrolledJugs : currentPersonAssignmentData.keySet()) {
            if (PersonEnrolledJugs.getPerson().getId() == person.getId() && PersonEnrolledJugs.checkIfPossibleToEnroll(jug.getFlavourId(), jug.getId())) {
                PersonEnrolledJugs.enrollJugToPerson(jug);
                return true;
            }
        }
        return false;
    }

    public Jug getByFlavourIfEnrolled(int flavourId, int personId) {
        for (PersonEnrolledJugs personEnrolledJugsJug : currentPersonAssignmentData.keySet()) {
            if (personEnrolledJugsJug.getPerson().getId() == personId) {
                Optional<Jug> byFlavour = personEnrolledJugsJug.getByFlavourId(flavourId);
                return byFlavour.orElse(null);
            }
        }
        return null;
    }

    public void calculatePersonSatisfactionFactors() {
        for (Map.Entry<PersonEnrolledJugs, List<Integer>> entry : currentPersonAssignmentData.entrySet()) {
            int satisfaction = entry.getKey().calculateSatisfaction();
            int dissatisfaction = entry.getKey().calculateDissatisfaction();

            entry.setValue(List.of(satisfaction, dissatisfaction));
        }
    }

    private double calculateSatisfactionToDissatisfactionRatio(Collection<List<Integer>> valuesCollection) {
        int satisfaction = 0;
        int dissatisfaction = 0;
        for (List<Integer> integerList : valuesCollection) {
            satisfaction += integerList.get(0);
            dissatisfaction += integerList.get(1);
        }
        if (dissatisfaction != 0) {
            double result = (double) satisfaction / dissatisfaction;
            ratio = result;
            return result;
        } else {
            ratio = satisfaction;
            return satisfaction;
        }
    }

    public void handleResults() {
        double currentSatisfactionRatio = this.calculateSatisfactionToDissatisfactionRatio(currentPersonAssignmentData.values());
        double bestResultSatisfactionRatio = this.calculateSatisfactionToDissatisfactionRatio(bestResult.values());

        List<String[]> resultInformationModels = ResultInformationParser.parseEnrolledMap(currentPersonAssignmentData);
        outputFileWriter.writeResults(resultInformationModels, currentSatisfactionRatio);

        if (currentSatisfactionRatio > bestResultSatisfactionRatio) {
            bestResult.clear();
            bestResult.putAll(currentPersonAssignmentData);
        }
        currentPersonAssignmentData.clear();
    }

    public int getOverallSatisfactionParams(boolean isCurrent, boolean isSatisfaction) {
        int index = isSatisfaction ? 0 : 1;
        int satisfaction = 0;
        if (isCurrent) {
            for (List<Integer> integerList : currentPersonAssignmentData.values()) {
                satisfaction += integerList.get(index);
            }
        } else {
            for (List<Integer> integerList : bestResult.values()) {
                satisfaction += integerList.get(index);
            }
        }
        return satisfaction;
    }

}
