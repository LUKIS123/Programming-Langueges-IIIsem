package pl.edu.pwr.lgawron.flow.tools;

import pl.edu.pwr.lgawron.repositories.EnrolledJugRepository;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;

public class ResultPrinter {
    private final JugRepository jugRepository;
    private final PersonRepository personRepository;
    private final EnrolledJugRepository enrolledJugRepository;

    public ResultPrinter(JugRepository jugRepository, PersonRepository personRepository, EnrolledJugRepository enrolledJugRepository) {
        this.jugRepository = jugRepository;
        this.personRepository = personRepository;
        this.enrolledJugRepository = enrolledJugRepository;
    }

    public void print() {
        System.out.println(jugRepository.getJugList());

        System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------------------\n");
        enrolledJugRepository.getPersonAssignmentData().forEach((enrolledJug, integer) -> {
            System.out.println(enrolledJug);
            System.out.println("\n");
        });
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------\n");

        System.out.println(jugRepository.getJugList());
        System.out.println(enrolledJugRepository);
    }
}
