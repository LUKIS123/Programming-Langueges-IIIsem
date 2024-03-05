package pl.edu.pwr.lgawron.lab07.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab07.common.utils.DataFileUtility;

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
 * - settings_client.txt
 * komenda: java -p . -m pl.edu.pwr.lgawron.lab07/pl.edu.pwr.lgawron.lab07.client.Main
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/pl/edu/pwr/lgawron/lab07/client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 450);
        stage.setTitle("Gadget Shop");
        stage.setScene(scene);
        stage.show();

        stage.setOnHidden(event -> {
            ClientAppController controller = fxmlLoader.getController();
            controller.onExitApplication();
            Platform.exit();
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // wczytywanie z pliku adresu hosta na ktorym wystawiana jest namiastka listenera
        System.setProperty("java.rmi.server.hostname", DataFileUtility.readFile("settings_client.txt").split("=")[1]);
        System.setProperty("java.security.policy", "java.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        launch();
    }

}
