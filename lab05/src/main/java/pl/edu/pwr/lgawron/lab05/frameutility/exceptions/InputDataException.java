package pl.edu.pwr.lgawron.lab05.frameutility.exceptions;

import java.io.Serial;

public class InputDataException extends Exception {
    @Serial
    private static final long serialVersionUID = 7718828512143293558L;

    public InputDataException(String message) {
        super(message);
    }
}
