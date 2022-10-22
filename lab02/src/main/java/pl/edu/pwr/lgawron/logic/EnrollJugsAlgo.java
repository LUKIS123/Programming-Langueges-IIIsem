package pl.edu.pwr.lgawron.logic;

import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.EnrolledJugsRepository;
import pl.edu.pwr.lgawron.repositories.JugRepository;

import java.util.List;

public class EnrollJugsAlgo {
    private final JugRepository jugRepository;
    private final EnrolledJugsRepository enrolledJugsRepository;

    public EnrollJugsAlgo(JugRepository jugRepository, EnrolledJugsRepository enrolledJugsRepository) {
        this.jugRepository = jugRepository;
        this.enrolledJugsRepository = enrolledJugsRepository;
    }

    public void runAlgo(List<Person> currentPersonListPermutation, boolean isGreedyAlgo) {
        enrolledJugsRepository.initRepository(currentPersonListPermutation);
        int maxIterationCount = jugRepository.getAllJugsVolume() / 100;

        for (int i = 0; i <= maxIterationCount; i++) {
            for (Person person : currentPersonListPermutation) {
                List<Integer> preferredFlavourIds = person.getPreferredFlavourIds();

                for (int flavourId : preferredFlavourIds) {
                    Jug matchingJug = findMatchingWithHighestVolume(flavourId, person.getId());
                    if (matchingJug != null && matchingJug.getVolume() != 0) {
                        if (enrolledJugsRepository.addPersonAssignmentData(person, matchingJug, isGreedyAlgo)) {
                            break;
                        }
                    }
                }

            }
        }
        enrolledJugsRepository.calculatePersonSatisfactionFactors();
        enrolledJugsRepository.handleResults();
        jugRepository.getJugList().forEach(Jug::resetVolume);
    }

    private Jug findMatchingWithHighestVolume(int flavourId, int personId) {
        List<Jug> byFlavour = jugRepository.getByFlavourId(flavourId);
        if (byFlavour.isEmpty()) {
            return null;
        }
        // Jug with flavour given was already assigned before
        Jug checkForFlavourIds = enrolledJugsRepository.getByFlavourIfEnrolled(flavourId, personId);
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
