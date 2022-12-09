module pl.edu.pwr.lgawron.lab06 {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.edu.pwr.lgawron.lab06.administrator to javafx.fxml;
    opens pl.edu.pwr.lgawron.lab06.player to javafx.fxml;

    exports pl.edu.pwr.lgawron.lab06.administrator;
    exports pl.edu.pwr.lgawron.lab06.player;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.parse;
    opens pl.edu.pwr.lgawron.lab06.mainlogic.parse to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.frame;
    opens pl.edu.pwr.lgawron.lab06.mainlogic.frame to javafx.fxml;
}