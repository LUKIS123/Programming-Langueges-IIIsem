package pl.edu.pwr.lgawron;

import pl.edu.pwr.lgawron.algo.NewAlgo;
import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;
import pl.edu.pwr.lgawron.repositories.JugRepository;
import pl.edu.pwr.lgawron.repositories.PersonRepository;
import pl.edu.pwr.lgawron.tools.DataParser;
import pl.edu.pwr.lgawron.tools.FileUtility;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 * sposob uruchamiania: java -jar .\lab02.jar jug_data.txt person_data.txt
 */

public class Main {
    public static void main(String[] args) {
        String jugDataFilePath = args[0];
        String personDataFilePath = args[1];

        FileUtility fileUtility = new FileUtility();
        List<String> jugDataLines = fileUtility.readDataLines(jugDataFilePath);
        List<String> personDataLines = fileUtility.readDataLines(personDataFilePath);

        DataParser dataParser = new DataParser();

        List<Jug> jugList = jugDataLines.stream().map(dataParser::parseJug).collect(Collectors.toList());
        List<Person> personList = personDataLines.stream().map(dataParser::parsePerson).collect(Collectors.toList());

        JugRepository jugRepository = new JugRepository(jugList);
        PersonRepository personRepository = new PersonRepository(personList);

//        JugEnrollAlgo algo = new JugEnrollAlgo(jugRepository, personRepository);
//        algo.ready();
//        algo.pour100ml();
//        algo.printResult();

        NewAlgo newAlgo = new NewAlgo(jugRepository, personRepository);
        newAlgo.pourDrinks();
    }
}
