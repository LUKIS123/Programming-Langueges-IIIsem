package pl.edu.pwr.lgawron.flow;

import pl.edu.pwr.lgawron.algo.ArrayListPermutationGenerator;
import pl.edu.pwr.lgawron.algo.EnrollJugsAlgo;
import pl.edu.pwr.lgawron.flow.tools.DataParser;
import pl.edu.pwr.lgawron.flow.tools.FileUtility;
import pl.edu.pwr.lgawron.flow.tools.ResultPrinter;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.EnrolledJugRepository;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFlow {
    private final String[] applicationArguments;
    private final FileUtility fileUtility;
    private final DataParser dataParser;
    private PersonRepository personRepository;

    public ApplicationFlow(String[] applicationArguments) {
        this.applicationArguments = applicationArguments;
        this.fileUtility = new FileUtility();
        this.dataParser = new DataParser();
    }

    public void runApp() {
        List<String> jugDataLines = fileUtility.readDataLines(applicationArguments[0]);
        List<String> personDataLines = fileUtility.readDataLines(applicationArguments[1]);

        // original data lists
        List<Jug> jugList = jugDataLines.stream().map(dataParser::parseJug).toList();
        List<Person> personList = personDataLines.stream().map(dataParser::parsePerson).toList();

        // implementation
        JugRepository jugRepository = new JugRepository(jugList);
        this.personRepository = new PersonRepository(personList);
        EnrolledJugRepository enrolledJugRepository = new EnrolledJugRepository(personRepository.getPersonList());
        EnrollJugsAlgo enrollAlgo = new EnrollJugsAlgo(jugRepository, personRepository, enrolledJugRepository);
        // main logic

        //enrollAlgo.runAlgo(null);


        // gather const data
        List<List<Integer>> getAllPermutations = new ArrayList<>();
        List<Integer> personIdList = personList.stream().map(Person::getId).toList();
        ArrayListPermutationGenerator arrayListPermutationGenerator = new ArrayListPermutationGenerator(personIdList);
        while (arrayListPermutationGenerator.hasMore()) {
            getAllPermutations.add(arrayListPermutationGenerator.getNext()); // values 0,1,2,3
        }

        for (List<Integer> variant : getAllPermutations) {
            List<Person> newPersonList = variant.stream().map(integer -> personRepository.getPersonList().get(integer)).toList();

            enrollAlgo.runAlgo(newPersonList);
        }

        // printing results
        ResultPrinter resultPrinter = new ResultPrinter(jugRepository, personRepository, enrolledJugRepository);
        resultPrinter.print();
    }
}
