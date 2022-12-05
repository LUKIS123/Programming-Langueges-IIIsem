module pl.edu.pwr.lgawron.lab06.lab06 {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.lgawron.lab06.lab06 to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.lab06;
}