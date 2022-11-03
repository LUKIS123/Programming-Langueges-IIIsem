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

        // test

//        List<Customer> c = new ArrayList<>();
//        c.add(new Customer(1, "kas"));
//        String s = new Gson().toJson(c);
//        System.out.println(s);
//
//        FileReader.writeJsonString(s, "customer_database.json");
//        String s1 = FileReader.readFile("customer_database.json");
//        JsonSerializeUtility<Customer> ser = new JsonSerializeUtility<>();
//        List<Customer> customers = ser.serializeFromJson(s1, Customer.class);
//        System.out.println("SERIALIZE TEST");
//        customers.forEach(System.out::println);

        // end of test

        var customerAppView = new CustomerAppView(args);
        customerAppView.runConsoleApp();
    }
}
