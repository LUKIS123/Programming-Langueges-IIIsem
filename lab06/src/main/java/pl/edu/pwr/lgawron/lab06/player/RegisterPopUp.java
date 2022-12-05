package pl.edu.pwr.lgawron.lab06.player;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegisterPopUp {

    public VBox renderPopUp() {

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Please enter your server and port:"));

        return dialogVbox;
    }
}
