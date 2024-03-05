package pl.edu.pwr.lgawron.lab06.common.frame;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegisterPopUp {

    public VBox renderPopUp(String s) {

        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text(s));

        return dialogVbox;
    }
}
