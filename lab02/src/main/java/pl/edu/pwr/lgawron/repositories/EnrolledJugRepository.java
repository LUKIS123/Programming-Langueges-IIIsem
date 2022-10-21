package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.EnrolledJug;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;

import java.util.*;

public class EnrolledJugRepository {
    // stores enrolledJug and satisfaction ratio
    private final Map<EnrolledJug, List<Integer>> currentPersonAssignmentData = new LinkedHashMap<>();
    private final Map<EnrolledJug, List<Integer>> bestResult = new LinkedHashMap<>();

    public EnrolledJugRepository(List<Person> personList) {
        personList.forEach(person -> currentPersonAssignmentData.put(new EnrolledJug(person), List.of(0, 0)));
    }

    public Map<EnrolledJug, List<Integer>> getCurrentPersonAssignmentData() {
        return currentPersonAssignmentData;
    }

    public Map<EnrolledJug, List<Integer>> getBestResult() {
        return bestResult;
    }

    public boolean addPersonAssignmentData(Person person, Jug jug) {
        for (EnrolledJug enrolled : currentPersonAssignmentData.keySet()) {
            if (enrolled.getPerson().getId() == person.getId() && enrolled.checkIfPossibleToEnroll(jug.getFlavourId(), jug.getId())) {
                enrolled.enrollJugToPerson(jug);
                return true;
            }
        }
        return false;
    }

    public Jug getByFlavourIfEnrolled(int flavourId, int personId) {
        for (EnrolledJug enrolledJug : currentPersonAssignmentData.keySet()) {
            if (enrolledJug.getPerson().getId() == personId) {
                Optional<Jug> byFlavour = enrolledJug.getByFlavourId(flavourId);
                return byFlavour.orElse(null);
            }
        }
        return null;
    }

    public void calculateSatisfactionRatios() {
        for (Map.Entry<EnrolledJug, List<Integer>> entry : currentPersonAssignmentData.entrySet()) {
            int satisfaction = entry.getKey().calculateSatisfaction();
            int dissatisfaction = entry.getKey().calculateDissatisfaction();

            entry.setValue(List.of(satisfaction, dissatisfaction));
        }
    }

    public double calculateSatisfactionToDissatisfactionRatio(Collection<List<Integer>> valuesCollection) {
        int satisfaction = 0;
        int dissatisfaction = 0;
        for (List<Integer> integerList : valuesCollection) {
            satisfaction += integerList.get(0);
            dissatisfaction += integerList.get(1);
        }
        return (double) satisfaction / dissatisfaction;
    }

    public void reset() {
        double currentSatisfactionRatio = this.calculateSatisfactionToDissatisfactionRatio(currentPersonAssignmentData.values());
        double bestResultSatisfactionRatio = this.calculateSatisfactionToDissatisfactionRatio(bestResult.values());
        if (currentSatisfactionRatio > bestResultSatisfactionRatio) {
            bestResultSatisfactionRatio = currentSatisfactionRatio;
        }
        currentPersonAssignmentData.clear();
    }

    @Override
    public String toString() {
        return "EnrolledJugRepository{" +
                "personAssignmentData=" + currentPersonAssignmentData +
                '}';
    }
}
