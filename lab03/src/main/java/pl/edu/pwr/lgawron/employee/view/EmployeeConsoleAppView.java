package pl.edu.pwr.lgawron.employee.view;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.employee.flow.EmployeeAppController;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeConsoleAppView {
    public static ActionResult login(LocalDate date) {
        System.out.println("Hello Employee, Today is: " + date);
        return ActionResult.MENU;
    }

    public static ActionResult menu() {
        System.out.println("1 - List products");
        System.out.println("2 - List customer complaints");
        System.out.println("3 - List manufacturer responses");
        System.out.println("4 - Forward to manufacturer");
        System.out.println("5 - Forward to customer");
        System.out.println("6 - Forward all");
        System.out.println("7 - Refresh current date");
        System.out.println("0 - Close app");

        int next = new Scanner(System.in).nextInt();
        switch (next) {
            case 1:
                return ActionResult.LIST_PRODUCTS;
            case 2:
                return ActionResult.LIST_COMPLAINTS;
            case 3:
                return ActionResult.LIST_RESPONSES;
            case 4:
                return ActionResult.FORWARD_TO_MANUFACTURER;
            case 5:
                return ActionResult.FORWARD_TO_CUSTOMER;
            case 6:
                return ActionResult.FORWARD_ALL;
            case 7:
                return ActionResult.REFRESH_DATE;
            case 0:
                return ActionResult.END;
        }
        return ActionResult.END;
    }

    public static ActionResult listProducts(List<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println("Sorry! You have no complains to list");
            return ActionResult.MENU;
        }
        productList.forEach(System.out::println);
        return ActionResult.MENU;
    }

    public static ActionResult listData(List<Reclamation> reclamationList) {
        if (reclamationList.isEmpty()) {
            System.out.println("Sorry! You have no complains to list");
            return ActionResult.MENU;
        }
        reclamationList.forEach(System.out::println);
        return ActionResult.MENU;
    }

    public static ActionResult refreshDate(EmployeeAppController controller) {
        controller.refreshDate();
        System.out.println("Current date is: " + controller.today);
        return ActionResult.MENU;
    }

    public static ActionResult forwardToManufacturer(EmployeeAppController controller) {
        System.out.println("Give reclamation ID:");
        int reclamationId = new Scanner(System.in).nextInt();
        boolean b = controller.sendReclamationToManufacturer(reclamationId);
        if (b)
            System.out.println("Success!");
        else
            System.out.println("Something went wrong!");

        return ActionResult.MENU;
    }

    public static ActionResult forwardToCustomer(EmployeeAppController controller) {
        System.out.println("Give reclamation ID:");
        int reclamationId = new Scanner(System.in).nextInt();
        boolean b = controller.sendBackToCustomer(reclamationId);
        if (b)
            System.out.println("Success!");
        else
            System.out.println("Something went wrong!");

        return ActionResult.MENU;
    }

    public static ActionResult forwardAll(EmployeeAppController controller) {
        controller.tryForwardAllReclamation();
        return ActionResult.MENU;
    }

}
