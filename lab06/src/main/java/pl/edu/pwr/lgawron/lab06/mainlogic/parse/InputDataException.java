package pl.edu.pwr.lgawron.lab06.mainlogic.parse;

import java.io.Serial;

public class InputDataException extends Exception {
    @Serial
    private static final long serialVersionUID = 7718828512143293558L;

    public InputDataException(String message) {
        super(message);
    }
}
