package pl.edu.pwr.lgawron.lab07.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
 * komenda: java -p . -m pl.edu.pwr.lgawron.lab06/pl.edu.pwr.lgawron.lab07.client.Main
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/pl/edu/pwr/lgawron/lab07/client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);
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

        // test
//        try {
//            Registry localhost = LocateRegistry.getRegistry("localhost", 8085);
//            IShop shop = (IShop) localhost.lookup("shopRemote");
//
//            Client client = new Client();
//            client.setName("Lukasz");
//            shop.register(client);
//            System.out.println("Added client");
//
//        } catch (Exception e) {
//            System.out.println("CLIENT ERROR: " + e);
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        launch();
    }
}
