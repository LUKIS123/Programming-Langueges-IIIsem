package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.EnrolledJug;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrolledJugRepository {
    // stores enrolledJug and satisfaction ratio
    private final Map<EnrolledJug, Integer> personAssignmentData = new LinkedHashMap<>();

    public EnrolledJugRepository(List<Person> personList) {
        personList.forEach(person -> personAssignmentData.put(new EnrolledJug(person), 0));
    }

    public Map<EnrolledJug, Integer> getPersonAssignmentData() {
        return personAssignmentData;
    }

    public boolean addPersonAssignmentData(Person person, Jug jug) {
        for (EnrolledJug enrolled : personAssignmentData.keySet()) {
            if (enrolled.getPerson().getId() == person.getId() && enrolled.checkIfPossibleToEnroll(jug.getFlavourId(), jug.getId())) {
                enrolled.enrollJugToPerson(jug);
                return true;
            }
        }
        return false;
    }

    public Jug getByFlavourIfEnrolled(int flavourId, int personId) {
        for (EnrolledJug enrolledJug : personAssignmentData.keySet()) {
            if (enrolledJug.getPerson().getId() == personId) {
                Optional<Jug> byFlavour = enrolledJug.getByFlavourId(flavourId);
                return byFlavour.orElse(null);
            }
        }
        return null;
    }

    public void calculateSatisfactionRatio() {
        for (Map.Entry<EnrolledJug, Integer> entry : personAssignmentData.entrySet()) {
            entry.setValue(entry.getKey().calculateSatisfaction());
        }
    }

    @Override
    public String toString() {
        return "EnrolledJugRepository{" +
                "personAssignmentData=" + personAssignmentData +
                '}';
    }
}
