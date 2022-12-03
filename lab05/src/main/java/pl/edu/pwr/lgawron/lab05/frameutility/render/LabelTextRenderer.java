package pl.edu.pwr.lgawron.lab05.frameutility.render;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class LabelTextRenderer {

    public static void renderDistributor(Label label, int id, boolean isUsed) {
        Platform.runLater(
                () -> {
                    if (!isUsed) {
                        label.setText("| ____free____ |");
                    } else {
                        label.setText("| used_by_as-" + String.valueOf(Character.toChars(id + 65)) + " |");
                    }
                });
    }

    public static void renderAssistants(Label label, int food, int id) {
        String s = String.valueOf(Character.toChars(id + 65));
        Platform.runLater(
                () -> {
                    if (food >= 10) {
                        label.setText("| as-" + s + "_" + food + " |");
                    } else {
                        label.setText("| as-" + s + "_0" + food + " |");
                    }
                }
        );
    }

    public static void renderEmptyAssistant(Label label) {
        Platform.runLater(
                () -> label.setText("| _________ |")
        );
    }

    public static void renderNourishment(Label label, int value, boolean isRefilled) {
        Platform.runLater(
                () -> {
                    if (isRefilled) {
                        label.setText("|r    " + value + "     |");
                    } else if (value == 10) {
                        label.setText("|    " + value + "     |");
                    } else {
                        label.setText("|     " + value + "     |");
                    }
                }
        );
    }

    public static void renderStamina(Label label, int stamina) {
        Platform.runLater(
                () -> label.setText("|     " + stamina + "     |")
        );
    }

    public static void renderEmptyAssistantsSpaces(Label label) {
        label.setText("| _________ |");
    }
}
