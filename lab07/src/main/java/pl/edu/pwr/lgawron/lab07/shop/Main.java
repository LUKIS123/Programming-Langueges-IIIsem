package pl.edu.pwr.lgawron.lab07.shop;

import javafx.application.Application;
import javafx.application.Platform;
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

        stage.setOnHidden(event -> {
            ShopAppController controller = fxmlLoader.getController();
            controller.onExitApplication();
            Platform.exit();
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}