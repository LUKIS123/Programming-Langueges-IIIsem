package pl.edu.pwr.lgawron.manufacturer.view;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.manufacturer.controller.ManufacturerAppController;

import java.util.List;
import java.util.Scanner;

public class ManufacturerConsoleAppView {
    public static ActionResult login(ManufacturerAppController controller) {
        System.out.println("Give your ID number:");
        int id = new Scanner(System.in).nextInt();
        if (controller.loginToDatabase(id)) {
            System.out.println("Hello " + controller.getUserName() + " ,Today is: " + controller.today);
            controller.setProductList(controller.getProductListById());
            return ActionResult.MENU;
        }

        System.out.println("ERROR: Wrong user ID, user does not exist!");
        return ActionResult.MENU;
    }

    public static ActionResult menu() {
        System.out.println("1 - List products");
        System.out.println("2 - List complaints");
        System.out.println("3 - Pick up complaints");
        System.out.println("4 - Answer to complaint");
        System.out.println("5 - Refresh current date");
        System.out.println("0 - Close app");

        int next = new Scanner(System.in).nextInt();
        switch (next) {
            case 1:
                return ActionResult.LIST_PRODUCTS;
            case 2:
                return ActionResult.LIST_COMPLAINTS;
            case 3:
                return ActionResult.PICK_UP_COMPLAINS;
            case 4:
                return ActionResult.ANSWER_COMPLAINT;
            case 5:
                return ActionResult.REFRESH_DATE;
            case 0:
                return ActionResult.END;
        }
        return ActionResult.END;
    }

    public static ActionResult listProducts(List<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println("List is empty");
        }
        productList.forEach(System.out::println);
        return ActionResult.MENU;
    }

    public static ActionResult listComplains(List<Reclamation> reclamationList) {
        if (reclamationList.isEmpty()) {
            System.out.println("List is empty");
        }
        reclamationList.forEach(System.out::println);
        return ActionResult.MENU;
    }

    public static ActionResult pickUpComplaints(ManufacturerAppController controller) {
        int i = controller.pickUpComplaints();
        System.out.println("Picked up: " + i + " complains");
        System.out.println("List complaints to check");
        return ActionResult.MENU;
    }

    public static ActionResult answerComplaint(ManufacturerAppController controller) {
        System.out.println("Give reclamation ID");
        int reclamationId = new Scanner(System.in).nextInt();
        System.out.println("Accept: <any key>, Reject: -1");
        int response = new Scanner(System.in).nextInt();
        boolean b = controller.answerComplaint(reclamationId, response);
        if (b)
            System.out.println("Success");
        else
            System.out.println("Something went wrong!");

        return ActionResult.MENU;
    }

    public static ActionResult refreshDate(ManufacturerAppController controller) {
        controller.refreshDate();
        System.out.println("Current date is: " + controller.today);
        return ActionResult.MENU;
    }

}
