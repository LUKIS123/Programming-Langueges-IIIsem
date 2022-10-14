package pl.edu.pwr.lgawron.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnrolledJug {
    private final Person person;
    private final Map<Jug, Integer> enrolledJugs = new LinkedHashMap<>();

    public EnrolledJug(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public Map<Jug, Integer> getEnrolledJugs() {
        return enrolledJugs;
    }

    //added
    public void assignJugToPerson(Jug jug) {
        if (enrolledJugs.get(jug) == null) {
            enrolledJugs.put(jug, 100);
        } else {
            enrolledJugs.replace(jug, enrolledJugs.get(jug) + 100);
        }
    }
}
