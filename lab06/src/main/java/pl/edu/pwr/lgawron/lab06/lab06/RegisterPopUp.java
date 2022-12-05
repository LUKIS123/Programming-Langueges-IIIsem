package pl.edu.pwr.lgawron.lab06.lab06;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class RegisterPopUp {

    static VBox createPopupContent() {
        final Label unfortunateEvent = new Label();

        unfortunateEvent.setWrapText(true);
        unfortunateEvent.setTextAlignment(TextAlignment.CENTER);

        final Button wand = new Button("xyzzy");
        final VBox wizBox = new VBox(5);
        wizBox.setAlignment(Pos.CENTER);
        wizBox.getChildren().setAll(
                wand
        );

        wand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                unfortunateEvent.setText(
                        "Confirm"
                );
            }
        });

        return wizBox;
    }
}
