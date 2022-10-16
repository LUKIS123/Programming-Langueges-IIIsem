package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.EnrolledJug;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnrolledJugRepository {
    // stores enrolledJug and satisfaction ratio
    private final Map<EnrolledJug, Integer> personAssignmentData = new LinkedHashMap<>();

    // wymysl cos aby za kolejna iteracja sprawdzało czy repo Lista enrolledJug zawiera juz dany dzban

    public EnrolledJugRepository(List<Person> personList) {
        personList.forEach(person -> personAssignmentData.put(new EnrolledJug(person), 0));
    }

    public Map<EnrolledJug, Integer> getPersonAssignmentData() {
        return personAssignmentData;
    }

    public boolean addPersonAssignmentData(Person person, Jug jug) {
        for (Map.Entry<EnrolledJug, Integer> entry : personAssignmentData.entrySet()) {
            if (entry.getKey().getPerson().getId() == person.getId() && entry.getKey().checkFlavourIds(jug.getFlavourId(), jug.getId())) {
                entry.getKey().enroll(jug);
                return true;
            }
        }
        return false;
    }

    public Jug checkForFlavourIds(int flavourId) {
        for (EnrolledJug enrolledJug : personAssignmentData.keySet()) {
            Jug flavourAlreadyPresent = enrolledJug.checkIfFlavourAlreadyPresent(flavourId);
            if (flavourAlreadyPresent != null) {
                return flavourAlreadyPresent;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnrolledJugRepository{" +
                "personAssignmentData=" + personAssignmentData +
                '}';
    }
}
