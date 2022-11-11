package pl.edu.pwr.lgawron.lab04;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
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
 * komenda: java -p . -m pl.edu.pwr.lgawron.lab04/pl.edu.pwr.lgawron.lab04.Application
 */


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 420);
        stage.setTitle("Hello!");


//        TextField l1 = new TextField("l1");
//        TextField l2 = new TextField("l1");
//        TextField d = new TextField("l1");
//        TextField h = new TextField("l1");
//
//        Button button = new Button("Ok");
//        TilePane pane = new TilePane();
//
//        Label label = new Label("button not selected");
//
//        EventHandler<ActionEvent> event = e -> {
//
//            ParseValues parseValues = new ParseValues();
//            parseValues.setL1(l1.getText());
//            parseValues.setL2(l2.getText());
//            parseValues.setD(d.getText());
//            parseValues.setH(h.getText());
//
//            label.setText(String.valueOf(parseValues.getL1()));
//        };
//
//        button.setOnAction(event);
//
//        pane.getChildren().add(l1);
//        pane.getChildren().add(l2);
//        pane.getChildren().add(d);
//        pane.getChildren().add(h);
//        pane.getChildren().add(button);
//        pane.getChildren().add(label);
//
//        Scene scene1 = new Scene(pane, 420, 420);
        //stage.setScene(scene1);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}