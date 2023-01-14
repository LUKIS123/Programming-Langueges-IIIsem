package pl.edu.pwr.lgawron.lab07.common.input;

public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 7718828512143293558L;

    public InvalidInputException(String message) {
        super(message);
    }
}
