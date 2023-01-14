package pl.edu.pwr.lgawron.lab07.shop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab07.common.utils.DataFileUtility;

import java.io.IOException;
import java.rmi.RMISecurityManager;

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
 * - gadgets.jar
 * - plik java.policy
 * - settings_shop.txt
 * komenda: java -p . -m pl.edu.pwr.lgawron.lab07/pl.edu.pwr.lgawron.lab07.shop.Main
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/pl/edu/pwr/lgawron/lab07/shop-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);
        stage.setTitle("Shop-App");
        stage.setScene(scene);

        stage.setOnHidden(event -> {
            ShopAppController controller = fxmlLoader.getController();
            controller.onExitApplication();
            Platform.exit();
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // wczytywanie z pliku adresu hosta na ktorym wystawiana jest namiastka IShop
        System.setProperty("java.rmi.server.hostname", DataFileUtility.readFile("settings_shop.txt").split("=")[1]);
        System.setProperty("java.security.policy", "java.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }


        launch();
    }

}