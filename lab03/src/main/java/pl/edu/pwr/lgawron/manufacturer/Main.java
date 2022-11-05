package pl.edu.pwr.lgawron.manufacturer;

import pl.edu.pwr.lgawron.businesslogic.utility.parse.ConsoleInputDataParser;

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
        int id = ConsoleInputDataParser.getId(args);


        // w application flow napisac logike dla wszytskich opcji - listowanie, complainy itd
        // klasa consoleView ktora w zaleznosci od wybranej opcji bedzie sobie wyswietlala co trzeba
        // moze poszukac cos o facotry i serwisach (complaint i manufacturer_response)
    }
}
