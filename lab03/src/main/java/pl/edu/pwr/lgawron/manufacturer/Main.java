package pl.edu.pwr.lgawron.manufacturer;

import pl.edu.pwr.lgawron.businesslogic.models.Customer;
import pl.edu.pwr.lgawron.businesslogic.utility.parse.ConsoleInputDataParser;
import pl.edu.pwr.lgawron.businesslogic.utility.JsonFileUtility;

import java.util.List;

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

        List<Customer> customerList = List.of(new Customer(1, "Roman_Bird"), new Customer(2, null));

        // JsonFileUtility<Customer> jsonFileUtility = new JsonFileUtility<>("customer_database.json");
        // jsonFileUtility.save(customerList);

        //  List<Customer> customerList2 = jsonFileUtility.load(Customer.class);
        //  customerList2.forEach(customer -> System.out.println(customer.toString()));


        // w application flow napisac logike dla wszytskich opcji - listowanie, complainy itd
        // klasa consoleView ktora w zaleznosci od wybranej opcji bedzie sobie wyswietlala co trzeba
        // moze poszukac cos o facotry i serwisach (complaint i manufacturer_response)
    }
}
