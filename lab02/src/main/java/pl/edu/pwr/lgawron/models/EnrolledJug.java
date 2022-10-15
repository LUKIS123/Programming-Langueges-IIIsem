package pl.edu.pwr.lgawron.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnrolledJug {
    private final Person person;
    // stores jug and how much volume was assigned to the person
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

    //added - delete after
    public void assignJugToPerson(Jug jug) {
        if (enrolledJugs.get(jug) == null) {
            enrolledJugs.put(jug, 100);
        } else {
            enrolledJugs.replace(jug, enrolledJugs.get(jug) + 100);
        }
    }

    // new
    public void enroll(Jug jug) {
        int vol = jug.getVolume();
        if (enrolledJugs.containsKey(jug) && vol != 0) {
            enrolledJugs.replace(jug, enrolledJugs.get(jug) + 100);
            jug.setVolume(vol - 100);
        } else {
            if (vol >= 400) {
                enrolledJugs.put(jug, 400);
                jug.setVolume(vol - 400);
            } else {
                enrolledJugs.put(jug, vol);
                jug.setVolume(0);
            }
        }
    }

    public boolean checkFlavourIds(int flavourId, int jugId) {
        if (enrolledJugs.isEmpty()) {
            return true;
        }
        for (Map.Entry<Jug, Integer> entry : enrolledJugs.entrySet()) {
            if (entry.getKey().getFlavourId() == flavourId && entry.getKey().getId() == jugId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "EnrolledJug{" +
                "person=" + person +
                ", enrolledJugs=" + enrolledJugs +
                '}';
    }
}
