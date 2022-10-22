package pl.edu.pwr.lgawron.flow;

import pl.edu.pwr.lgawron.algo.EnrollJugsAlgo;
import pl.edu.pwr.lgawron.algo.PermutationGenerator;
import pl.edu.pwr.lgawron.flow.tools.DataParser;
import pl.edu.pwr.lgawron.flow.tools.InputFileUtility;
import pl.edu.pwr.lgawron.resultutility.ResultPrinter;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.EnrolledJugsRepository;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;

import java.util.Arrays;
import java.util.List;

public class ApplicationFlow {
    private final String[] applicationArguments;
    private final InputFileUtility fileUtility;
    private final DataParser dataParser;

    public ApplicationFlow(String[] applicationArguments) {
        this.applicationArguments = applicationArguments;
        this.fileUtility = new InputFileUtility();
        this.dataParser = new DataParser();
    }

    public void runApp() {
        // reading input data from txt
        List<String> jugDataLines = fileUtility.readDataLines(applicationArguments[0]);
        List<String> personDataLines = fileUtility.readDataLines(applicationArguments[1]);

        // original data lists
        List<Jug> jugList = jugDataLines.stream().map(dataParser::parseJug).toList();
        List<Person> personList = personDataLines.stream().map(dataParser::parsePerson).toList();

        // implementation
        JugRepository jugRepository = new JugRepository(jugList);
        PersonRepository personRepository = new PersonRepository(personList);
        EnrolledJugsRepository enrolledJugsRepository = new EnrolledJugsRepository();
        EnrollJugsAlgo enrollAlgo = new EnrollJugsAlgo(jugRepository, enrolledJugsRepository);

        // main logic
        PermutationGenerator permutationGenerator = new PermutationGenerator(personList.size());
        while (permutationGenerator.hasMore()) {
            int[] next = permutationGenerator.getNext();
            List<Person> newPersonList = Arrays.stream(next).mapToObj(integer -> personRepository.getPersonList().get(integer)).toList();
            enrollAlgo.runAlgo(newPersonList);
        }

        // printing results
        ResultPrinter resultPrinter = new ResultPrinter(enrolledJugsRepository);
        resultPrinter.print();
    }
}
