package pl.edu.pwr.lgawron.models;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Jug> getByFlavourId(int flavourId) {
        return enrolledJugs.keySet().stream().filter(enrolled -> enrolled.getFlavourId() == flavourId).findFirst();
    }

    public void enrollJugToPerson(Jug jug) {
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

    public boolean checkIfPossibleToEnroll(int flavourId, int jugId) {
        // in case no Jugs were enrolled
        if (enrolledJugs.isEmpty()) {
            return true;
        }

        Optional<Jug> byFlavour = this.getByFlavourId(flavourId);
        // in case Jug with given flavour was already added, it must be the same one
        if (byFlavour.isPresent()) {
            if (byFlavour.get().getFlavourId() == flavourId && byFlavour.get().getId() == jugId) {
                return true;
            }
        }
        // in case no Jug with given flavour was added
        return byFlavour.isEmpty();
    }

    @Override
    public String toString() {
        return "EnrolledJug{" +
                "person=" + person +
                ", enrolledJugs=" + enrolledJugs +
                '}';
    }
}
