package pl.edu.pwr.lgawron.businesslogic.utility.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class DatabaseSaveException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 7718828512143293558L;

    public DatabaseSaveException(String s) {
        super(s);
    }
}