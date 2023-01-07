package pl.edu.pwr.lgawron.lab07.shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 * sposob uruchamiania:
 * Należy dodać do katalogu z modułem projektu:
 * - javafx-base.jar
 * - javafx-controls.jar
 * - javafx-fxml.jar
 * - javafx-graphics.jar
 * komenda: java -p . -m pl.edu.pwr.lgawron.lab06/pl.edu.pwr.lgawron.lab07.shop.Main
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/pl/edu/pwr/lgawron/lab07/shop-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Shop-App");
        stage.setScene(scene);
        stage.show();

        // test
        // zmienic w kontrolerze, nie potrzebuje proxy! lab06 admin tez?
//        try {
//            Registry registry = LocateRegistry.createRegistry(8085);
//
//
//            ShopImplementation shop = new ShopImplementation();
//            ItemType itemType = new ItemType();
//            itemType.setName("test123");
//            itemType.setCategory(1);
//            itemType.setPrice(2.0F);
//            shop.addToList(itemType);
//
//            IShop s = (IShop) UnicastRemoteObject.exportObject(shop, 0);
//            registry.rebind("t123", s);
//            System.out.println("Success!");
//        } catch (RemoteException e) {
//            System.out.println("SHOP ERROR: " + e);
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        launch();
    }
}