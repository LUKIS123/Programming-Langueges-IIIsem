module pl.edu.pwr.lgawron.lab06 {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.edu.pwr.lgawron.lab06.administrator to javafx.fxml;
    opens pl.edu.pwr.lgawron.lab06.player to javafx.fxml;

    exports pl.edu.pwr.lgawron.lab06.administrator to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.player;
    exports pl.edu.pwr.lgawron.lab06.player.parse;
    opens pl.edu.pwr.lgawron.lab06.player.parse to javafx.fxml;
}