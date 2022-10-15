package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.EnrolledJug;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrolledJugRepository {
    // stores enrolledJug and satisfaction ratio
    private final Map<EnrolledJug, Integer> personAssignmentData = new HashMap<>();

    // wymysl cos aby za kolejna iteracja sprawdzało czy repo Lista enrolledJug zawiera juz dany dzban

    public EnrolledJugRepository(List<Person> personList) {
        personList.forEach(person -> personAssignmentData.put(new EnrolledJug(person), 0));
    }

    public Map<EnrolledJug, Integer> getPersonAssignmentData() {
        return personAssignmentData;
    }

    public void addPersonAssignmentData(Person person, Jug jug) {
        for (Map.Entry<EnrolledJug, Integer> entry : personAssignmentData.entrySet()) {
            if (entry.getKey().getPerson().getId() == person.getId() && entry.getKey().checkFlavourIds(jug.getFlavourId(), jug.getId())) {
                entry.getKey().enroll(jug);
            }
        }
    }

    @Override
    public String toString() {
        return "EnrolledJugRepository{" +
                "personAssignmentData=" + personAssignmentData +
                '}';
    }
}
