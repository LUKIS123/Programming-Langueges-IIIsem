package pl.edu.pwr.lgawron.lab05.frameutility;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class LabelTextUtility {
    public static void printOnLabel(Label label, int value) {
        Platform.runLater(
                () -> label.setText("|   " + value + "   |")
        );
    }
}
