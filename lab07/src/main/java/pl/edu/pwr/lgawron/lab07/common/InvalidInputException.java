package pl.edu.pwr.lgawron.lab07.common;

import java.io.Serial;

public class InvalidInputException extends Exception {
    @Serial
    private static final long serialVersionUID = 7718828512143293558L;

    public InvalidInputException(String message) {
        super(message);
    }
}
