package pl.edu.pwr.lgawron.tools;

import pl.edu.pwr.lgawron.models.Jug;
import pl.edu.pwr.lgawron.models.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataParser {
    public Jug parseJug(String jugData) {
        String[] strings = jugData.split(";");
        return new Jug(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
    }

    public Person parsePerson(String personData) {
        String[] strings = personData.split(";");
        String[] flavours = strings[1].split(",");
        List<Integer> flavourIdList = Arrays.stream(flavours).map(Integer::parseInt).collect(Collectors.toList());
        return new Person(Integer.parseInt(strings[0]), flavourIdList);
    }
}
