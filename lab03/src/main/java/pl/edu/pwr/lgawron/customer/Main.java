package pl.edu.pwr.lgawron.customer;

import com.google.gson.Gson;
import pl.edu.pwr.lgawron.businesslogic.models.Customer;
import pl.edu.pwr.lgawron.businesslogic.models.Manufacturer;
import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.utility.database.DataFileUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.database.JsonSerializeUtility;
import pl.edu.pwr.lgawron.customer.view.CustomerAppView;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

        List<Customer> c = new ArrayList<>();
        c.add(new Customer(1, "Lukas"));
        c.add(new Customer(2, "Philip"));
        String s = new Gson().toJson(c);
        DataFileUtility.writeJsonString(s, "customer_database.json");

        List<Manufacturer> m = new ArrayList<>();
        m.add(new Manufacturer(1, "DeWalt"));
        m.add(new Manufacturer(2, "Stihl"));
        DataFileUtility.writeJsonString(new Gson().toJson(m), "manufacturer_database.json");

        List<Product> p = new ArrayList<>();
        p.add(new Product(1, 1, "Drill"));
        p.add(new Product(2, 2, "Chainsaw"));
        p.add(new Product(3, 2, "Lawnmower"));
        DataFileUtility.writeJsonString(new Gson().toJson(p), "product_database.json");
        // end of test

        var customerAppView = new CustomerAppView(args);
        customerAppView.runConsoleApp();
    }
}
