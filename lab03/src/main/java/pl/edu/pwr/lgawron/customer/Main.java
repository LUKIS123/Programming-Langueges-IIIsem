package pl.edu.pwr.lgawron.customer;

import pl.edu.pwr.lgawron.customer.view.CustomerAppView;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 * sposob uruchamiania: java -p lab03.jar -m lab03.main/pl.edu.pwr.lgawron.customer.Main [ID]
 * [ID] oznacza id klienta
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Customer " + args[0] + "!");
        var customerAppView = new CustomerAppView(args);
        customerAppView.runConsoleApp();
    }
}
