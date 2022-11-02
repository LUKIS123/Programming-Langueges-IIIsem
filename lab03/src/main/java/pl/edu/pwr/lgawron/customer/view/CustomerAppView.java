package pl.edu.pwr.lgawron.customer.view;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.customer.flow.CustomerAppFlow;

import java.util.List;
import java.util.Scanner;

public class CustomerAppView {
    private final CustomerAppFlow appFlow;

    public CustomerAppView(String[] consoleInput) {
        this.appFlow = new CustomerAppFlow(consoleInput);
    }

    public void runConsoleApp() {
        boolean isRunning = true;

        while (isRunning) {

            Scanner scanner = new Scanner(System.in);
            int value = scanner.nextInt();

            switch (value) {
                case 0 -> {
                    System.out.println("Application was closed!");
                    isRunning = false;
                }
                case 1 -> {
                    List<Product> products = appFlow.listProducts();
                    products.forEach(System.out::println);
                }
                default -> System.out.println("Unexpected value! Try again");
            }

        }
    }
}
