module pl.edu.pwr.lgawron.lab05 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.base;

    opens pl.edu.pwr.lgawron.lab05 to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab05;
}