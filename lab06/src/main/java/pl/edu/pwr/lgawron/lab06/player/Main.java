package pl.edu.pwr.lgawron.lab06.player;

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
 * komenda: java -p . -m pl.edu.pwr.lgawron.lab06/pl.edu.pwr.lgawron.lab06.player.Main
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/pl/edu/pwr/lgawron/lab06/player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1050, 900);
        stage.setTitle("Treasure Island");
        stage.setScene(scene);

        stage.setOnHidden(event -> {
            AppController controller = fxmlLoader.getController();
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