package pl.edu.pwr.lgawron.lab05.frameutility;

import pl.edu.pwr.lgawron.lab05.frameutility.exceptions.InputDataException;

import java.util.List;
import java.util.stream.Stream;

public class TextFieldParser {
    public static List<Integer> parseSimulationValues(String organismNumber, String labTechnicianNumber, String minSleepTime) throws NumberFormatException, InputDataException {
        List<Integer> integerList = Stream
                .of(organismNumber, labTechnicianNumber, minSleepTime)
                .map(Integer::parseInt).toList();

        if (integerList.get(0) < integerList.get(1)) {
            throw new InputDataException("There can't be more assistants than organisms");
        }

        if (integerList.get(0) > 20) {
            throw new InputDataException("Too many organisms");
        }

        return integerList;
    }
}
