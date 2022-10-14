package pl.edu.pwr.lgawron.algo;

import pl.edu.pwr.lgawron.models.EnrolledJug;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JugEnrollAlgo {
    public int howMany = 0;
    // public List<EnrolledJug> enrolledList = new ArrayList<>();

    private final JugRepository jugRepository;
    private final PersonRepository personRepository;
    private Map<Person, List<EnrolledJug>> personsEnrolledJugs;

    public JugEnrollAlgo(JugRepository jugRepository, PersonRepository personRepository) {
        this.jugRepository = jugRepository;
        this.personRepository = personRepository;
    }

    public void ready() {
        jugRepository.getJugList().forEach(jug -> howMany += jug.getVolume());
    }

    public void pour100ml() {
        List<Jug> jugList = jugRepository.getJugList();
        List<Person> personList = personRepository.getPersonList();

        //personList.forEach(person -> enrolledList.add(new EnrolledJug(person)));

        for (int i = 0; i < (howMany / 100); i++) {
            if (howMany == 0) {
                break;
            }
//            for (EnrolledJug jug : enrolledList) {
//                Integer firstFlavourId = jug.getPerson().getPreferredFlavourIds().get(0);
//
//
//                if (howMany == 0) {
//                    break;
//                }
            for (Person person : personList) {
                List<Integer> flavours = person.getPreferredFlavourIds();
                // Stream<Integer> integerStream = flavours.stream().filter(f -> jugRepository.getByFlavourId(f).get().getFlavourId() == f);


                if (howMany == 0) {
                    break;
                }
            }
        }
    }

    private Jug findMatching(List<Integer> flavourIds) {
        int i = 1;
        List<Jug> byFlavour = jugRepository.getJugList().stream().filter(jugs -> jugs.getFlavourId() == i).collect(Collectors.toList());
        Optional<Jug> foundMatch = byFlavour.stream().filter(jug -> jug.getVolume() != 0).findFirst();

        if (foundMatch.isPresent()) {
            return foundMatch.get();
        }

//    List<Jug> byFlavour = jugList.stream().filter(jugs -> jugs.getFlavourId() == flavourId).collect(Collectors.toList());
//        return byFlavour.stream().filter(jug -> jug.getVolume() != 0).findFirst();
        return null;
    }
}