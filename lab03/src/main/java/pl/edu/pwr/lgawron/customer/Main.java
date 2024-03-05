package pl.edu.pwr.lgawron.customer;

import pl.edu.pwr.lgawron.customer.controller.CustomerAppController;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 * <p>
 * sposob uruchamiania:
 * W folderze umiescic p lab03_pop.jar oraz lacznie 6 plikow oraz gson-2.10.jar,
 * pliki: date.txt, customer_compaint_database.json, customer_database.json, manufacturer_database.json, manufacturer_response_database.json, products_database.json
 * <p>
 * komenda: java -p . -m lab03.main/pl.edu.pwr.lgawron.customer.Main
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Customer!");
        CustomerAppController controller = new CustomerAppController();
        controller.index();
    }
}
