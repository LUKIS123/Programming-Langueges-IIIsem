module pl.edu.pwr.lgawron.lab07 {
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.edu.pwr.lgawron.lab07.common;

    exports pl.edu.pwr.lgawron.lab07.shop;
    opens pl.edu.pwr.lgawron.lab07.shop to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.client;
    opens pl.edu.pwr.lgawron.lab07.client to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.seller;
    opens pl.edu.pwr.lgawron.lab07.seller to javafx.fxml;
}