package pl.edu.pwr.lgawron;

import java.io.Serial;
import java.io.Serializable;

public class MyException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 7718828512143293558L;
    int i = 123;
}