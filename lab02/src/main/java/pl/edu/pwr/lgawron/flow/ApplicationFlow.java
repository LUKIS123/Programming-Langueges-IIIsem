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

        // original data lists
        List<Jug> originalJugList = jugDataLines.stream().map(dataParser::parseJug).toList();
        List<Person> originalPersonList = personDataLines.stream().map(dataParser::parsePerson).toList();
        JugRepository originalJugRepo = new JugRepository(originalJugList);
        PersonRepository originalPersonRepo = new PersonRepository(originalPersonList);

        // implementation
        this.jugRepository = new JugRepository(new ArrayList<>(originalJugList));
        this.personRepository = new PersonRepository(new ArrayList<>(originalPersonList));
        this.enrolledJugRepository = new EnrolledJugRepository(personRepository.getPersonList());
        this.enrollAlgo = new EnrollJugsAlgo(jugRepository, personRepository, enrolledJugRepository);
        // main logic

        //enrollAlgo.runAlgo(null);

        // printing results
        this.resultPrinter = new ResultPrinter(jugRepository, personRepository, enrolledJugRepository);
        resultPrinter.print();

        // gather const data
        List<List<Integer>> getAllPermutations = new ArrayList<>();
        List<Integer> personIdList = originalPersonList.stream().map(Person::getId).toList();
        ArrayListPermutationGenerator arrayListPermutationGenerator = new ArrayListPermutationGenerator(personIdList);
        while (arrayListPermutationGenerator.hasMore()) {
            getAllPermutations.add(arrayListPermutationGenerator.getNext()); // values 0,1,2,3
        }

        for (List<Integer> variant : getAllPermutations) {
            List<Person> newPersonList = variant.stream().map(integer -> originalPersonRepo.getPersonList().get(integer)).toList();
            //System.out.println(newPersonList); //działa!!!

            enrollAlgo.runAlgo(newPersonList);
            // zmienic pole na lokalne przekazywane do funkcji nie na globalne(personList)


        }
        // Do zrobienia: w klasie EnrolledJugRepository zrobić druga mape -> current i best result i podmieniac w razie potrzeby

    }
}
