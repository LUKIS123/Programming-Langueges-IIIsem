package pl.edu.pwr.lgawron.algo;

import pl.edu.pwr.lgawron.models.EnrolledJug;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.EnrolledJugRepository;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NewAlgo {
    private final JugRepository jugRepository;
    private final PersonRepository personRepository;
    private final Set<EnrolledJug> personEnrolledJugs = new HashSet<>();
    private final EnrolledJugRepository enrolledJugRepository;

    public NewAlgo(JugRepository jugRepository, PersonRepository personRepository) {
        this.jugRepository = jugRepository;
        this.personRepository = personRepository;
        this.enrolledJugRepository = new EnrolledJugRepository(personRepository.getPersonList());
    }

    public void pourDrinks() {
        List<Jug> jugList = jugRepository.getJugList();
        List<Person> personList = personRepository.getPersonList();
        // printing
        System.out.println(jugList);
        // delete after

        for (int i = 0; i <= 13; i++) {

            for (Person person : personList) {
                //
                //EnrolledJug enrolledJug = new EnrolledJug(person);
                //
                List<Integer> flavours = person.getPreferredFlavourIds();

                for (int flavour : flavours) {
                    Jug matchingJug = findMatchingWithHighestVolume(flavour);
                    if (matchingJug != null && matchingJug.getVolume() != 0) {
                        //
                        //enrolledJug.enroll(matchingJug);
                        //personEnrolledJugs.add(enrolledJug);
                        //
                        enrolledJugRepository.addPersonAssignmentData(person, matchingJug);
                        break;
                    }
                }
            }
        }

        // ! cos jest nie tak z nalewaniem, liczeniem objetosci
        // UPDATE!!!! -> chyba jednak działa :))
        // DO ZROBIENIA: policzyc ile iteracji petli, Licznik niezadowolenia, pozniej może jakies shuffle ale wymaga to zapisania rezustatow gdzies i reset

        // printing
        System.out.println("REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO\n");
        enrolledJugRepository.getPersonAssignmentData().forEach((enrolledJug, integer) -> {
            System.out.println(enrolledJug);
            System.out.println("\n");
        });
        System.out.println("REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO REPO\n");

//        personEnrolledJugs.forEach(enrolledJug -> {
//            System.out.println(enrolledJug);
//        });

        System.out.println(jugList);
        // delete after
    }

    private Jug findMatchingWithHighestVolume(int flavourId) {
        List<Jug> byFlavour = jugRepository.getJugList().stream().filter(jugs -> jugs.getFlavourId() == flavourId).collect(Collectors.toList());
        // Optional<Jug> foundMatch = byFlavour.stream().filter(jug -> jug.getVolume() != 0).findFirst();
        if (byFlavour.isEmpty()) {
            return null;
        } else {
            Jug foundMatch = byFlavour.get(0);
            for (Jug jug : byFlavour) {
                if (jug.getVolume() > foundMatch.getVolume()) {
                    foundMatch = jug;
                }
            }
            // return foundMatch.orElse(null);
            return foundMatch;
        }
    }
}
