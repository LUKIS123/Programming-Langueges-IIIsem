package pl.edu.pwr.lgawron.manufacturer;

import pl.edu.pwr.lgawron.business.logic.InputDataParser;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 * sposob uruchamiania: java -p lab03.jar -m lab03.main/pl.edu.pwr.lgawron.manufacturer.Main [ID]
 * [ID] oznacza id producenta
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Manufacturer!");
        int id = InputDataParser.getId(args);
    }
}
