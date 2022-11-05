package pl.edu.pwr.lgawron.customer.view;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.customer.controller.CustomerAppController;

import java.util.List;
import java.util.Scanner;

public class CustomerConsoleAppView {
    public static ActionResult login(CustomerAppController controller) {
        System.out.println("Give your ID number:");
        int id = new Scanner(System.in).nextInt();
        if (controller.loginToDatabase(id)) {
            System.out.println("Hello " + controller.getUserName() + " ,Today is: " + controller.today);
            return ActionResult.MENU;
        }

        System.out.println("ERROR: Wrong user ID, user does not exist!");
        return ActionResult.END;
    }

    public static ActionResult menu() {
        System.out.println("1 - List products");
        System.out.println("2 - List complaints");
        System.out.println("3 - Make a complaint");
        System.out.println("4 - Delete complaint");
        System.out.println("5 - Refresh current date");
        System.out.println("6 - Pick up product");
        System.out.println("0 - Close app");

        int next = new Scanner(System.in).nextInt();
        switch (next) {
            case 1:
                return ActionResult.LIST_PRODUCTS;
            case 2:
                return ActionResult.LIST_COMPLAINTS;
            case 3:
                return ActionResult.ADD_COMPLAINT;
            case 4:
                return ActionResult.DELETE_COMPLAINT;
            case 5:
                return ActionResult.REFRESH_DATE;
            case 6:
                return ActionResult.PICK_UP;
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

    public static ActionResult listComplaints(List<Reclamation> reclamationList) {
        if (reclamationList.isEmpty()) {
            System.out.println("Sorry! You have no complains to list");
            return ActionResult.MENU;
        }
        reclamationList.forEach(System.out::println);
        return ActionResult.MENU;
    }

    public static ActionResult addComplaint(CustomerAppController controller) {
        System.out.println("Give product ID");
        int productId = new Scanner(System.in).nextInt();
        System.out.println("Give description");
        String complaintDescription = new Scanner(System.in).nextLine();

        controller.addCustomerReclamation(productId, complaintDescription);
        return ActionResult.MENU;
    }

    public static ActionResult deleteComplaintIfPossible(CustomerAppController controller) {
        System.out.println("Give reclamation ID");
        int reclamationId = new Scanner(System.in).nextInt();

        boolean b = controller.deleteReclamation(reclamationId);
        if (b)
            System.out.println("Success!");
        else
            System.out.println("Reclamation already handed over!");
        return ActionResult.MENU;
    }

    public static ActionResult refreshDate(CustomerAppController controller) {
        controller.refreshDate();
        System.out.println("Current date is: " + controller.today);
        return ActionResult.MENU;
    }

    public static ActionResult pickUp(CustomerAppController controller) {
        List<Integer> integers = controller.pickUp();
        if (integers.isEmpty()) {
            System.out.println("You have no products to pick up!");
            return ActionResult.MENU;
        }
        integers.forEach(integer -> System.out.println("Picked up product referring to reclamation ID: " + integer));
        return ActionResult.MENU;
    }

}
