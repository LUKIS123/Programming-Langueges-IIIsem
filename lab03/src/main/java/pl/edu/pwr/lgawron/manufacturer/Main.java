package pl.edu.pwr.lgawron.manufacturer;

import pl.edu.pwr.lgawron.manufacturer.controller.ManufacturerAppController;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 *
 * sposob uruchamiania:
 * W folderze umiescic p lab03_pop.jar oraz lacznie 6 plikow oraz gson-2.10.jar,
 * pliki: date.txt, customer_compaint_database.json, customer_database.json, manufacturer_database.json, manufacturer_response_database.json, products_database.json
 *
 * komenda: java -p . -m lab03.main/pl.edu.pwr.lgawron.manufacturer.Main
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Manufacturer!");
        ManufacturerAppController controller = new ManufacturerAppController();
        controller.index();
    }
}
