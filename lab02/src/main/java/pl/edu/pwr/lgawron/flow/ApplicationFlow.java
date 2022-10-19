package pl.edu.pwr.lgawron.flow;

import pl.edu.pwr.lgawron.algo.EnrollJugsAlgo;
import pl.edu.pwr.lgawron.flow.tools.DataParser;
import pl.edu.pwr.lgawron.flow.tools.FileUtility;
import pl.edu.pwr.lgawron.flow.tools.ResultPrinter;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.EnrolledJugRepository;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;

import java.util.List;

public class ApplicationFlow {
    private final String[] applicationArguments;
    private final FileUtility fileUtility;
    private final DataParser dataParser;
    private JugRepository jugRepository;
    private PersonRepository personRepository;
    private EnrolledJugRepository enrolledJugRepository;
    private EnrollJugsAlgo enrollAlgo;
    private ResultPrinter resultPrinter;

    public ApplicationFlow(String[] applicationArguments) {
        this.applicationArguments = applicationArguments;
        this.fileUtility = new FileUtility();
        this.dataParser = new DataParser();
    }

    public void runApp() {
        List<String> jugDataLines = fileUtility.readDataLines(applicationArguments[0]);
        List<String> personDataLines = fileUtility.readDataLines(applicationArguments[1]);

        List<Jug> jugList = jugDataLines.stream().map(dataParser::parseJug).toList();
        List<Person> personList = personDataLines.stream().map(dataParser::parsePerson).toList();

        this.jugRepository = new JugRepository(jugList);
        this.personRepository = new PersonRepository(personList);
        this.enrolledJugRepository = new EnrolledJugRepository(personRepository.getPersonList());
        this.enrollAlgo = new EnrollJugsAlgo(jugRepository, personRepository, enrolledJugRepository);
        // main logic

        enrollAlgo.runAlgo();

        // printing results
        this.resultPrinter = new ResultPrinter(jugRepository, personRepository, enrolledJugRepository);
        resultPrinter.print();
    }
}
