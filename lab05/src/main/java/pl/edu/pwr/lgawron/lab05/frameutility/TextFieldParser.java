package pl.edu.pwr.lgawron.lab05.frameutility;

import javafx.scene.control.TextField;

public class TextFieldParser {
    public static int parseToIntegerValue(TextField textField) throws NumberFormatException {
        return Integer.parseInt(textField.getText());
    }

}
