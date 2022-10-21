package pl.edu.pwr.lgawron.algo;

import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.EnrolledJugRepository;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;

import java.util.List;

public class EnrollJugsAlgo {
    private final JugRepository jugRepository;
    private final PersonRepository personRepository;
    private final EnrolledJugRepository enrolledJugRepository;

    public EnrollJugsAlgo(JugRepository jugRepository, PersonRepository personRepository, EnrolledJugRepository enrolledJugRepository) {
        this.jugRepository = jugRepository;
        this.personRepository = personRepository;
        this.enrolledJugRepository = enrolledJugRepository;
    }

    public void runAlgo(List<Person> currentPersonListPermutation) {
        //List<Person> personList = personRepository.getPersonList();
        List<Person> personList = currentPersonListPermutation;
        int maxIterationCount = jugRepository.getAllJugsVolume() / 100;

        for (int i = 0; i <= maxIterationCount; i++) {
            for (Person person : personList) {
                List<Integer> preferredFlavourIds = person.getPreferredFlavourIds();

                for (int flavourId : preferredFlavourIds) {
                    Jug matchingJug = findMatchingWithHighestVolume(flavourId, person.getId());
                    if (matchingJug != null && matchingJug.getVolume() != 0) {
                        if (enrolledJugRepository.addPersonAssignmentData(person, matchingJug)) {
                            break;
                        }
                    }
                }

            }
        }
        enrolledJugRepository.calculateSatisfactionRatios();
        enrolledJugRepository.reset(currentPersonListPermutation);
        jugRepository.getJugList().forEach(Jug::reset);
    }

    private Jug findMatchingWithHighestVolume(int flavourId, int personId) {
        List<Jug> byFlavour = jugRepository.getByFlavourId(flavourId);
        if (byFlavour.isEmpty()) {
            return null;
        }
        // Jug with flavour given was already assigned before
        Jug checkForFlavourIds = enrolledJugRepository.getByFlavourIfEnrolled(flavourId, personId);
        if (checkForFlavourIds != null && checkForFlavourIds.getVolume() != 0) {
            return checkForFlavourIds;
        } else {
            // no Jug with flavour given was assigned
            Jug foundMatch = byFlavour.get(0);
            for (Jug jug : byFlavour) {
                if (jug.getVolume() > foundMatch.getVolume()) {
                    foundMatch = jug;
                }
            }
            return foundMatch;
        }
    }
}
