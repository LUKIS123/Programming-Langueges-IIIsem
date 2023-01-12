module pl.edu.pwr.lgawron.lab07 {
    requires javafx.controls;
    requires javafx.fxml;
    requires gadgets;
    requires java.rmi;
    requires java.desktop;

    exports pl.edu.pwr.lgawron.lab07.client.flow;
    exports pl.edu.pwr.lgawron.lab07.shop;
    opens pl.edu.pwr.lgawron.lab07.shop to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.client;
    opens pl.edu.pwr.lgawron.lab07.client to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.seller;
    opens pl.edu.pwr.lgawron.lab07.seller to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.shop.flow;
    opens pl.edu.pwr.lgawron.lab07.shop.flow to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.shop.services;
    opens pl.edu.pwr.lgawron.lab07.shop.services to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.shop.modelsextended;
    exports pl.edu.pwr.lgawron.lab07.common.input;
    exports pl.edu.pwr.lgawron.lab07.shop.repositories;
    opens pl.edu.pwr.lgawron.lab07.shop.repositories to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.common.listener;
    opens pl.edu.pwr.lgawron.lab07.common.listener to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab07.client.utils;
    opens pl.edu.pwr.lgawron.lab07.client.utils to javafx.fxml;
}