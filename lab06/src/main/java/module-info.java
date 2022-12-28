module pl.edu.pwr.lgawron.lab06 {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.edu.pwr.lgawron.lab06.administrator to javafx.fxml;
    opens pl.edu.pwr.lgawron.lab06.player to javafx.fxml;

    exports pl.edu.pwr.lgawron.lab06.administrator;
    exports pl.edu.pwr.lgawron.lab06.player;
    exports pl.edu.pwr.lgawron.lab06.common.input;
    exports pl.edu.pwr.lgawron.lab06.common.game.objects;
    exports pl.edu.pwr.lgawron.lab06.common.game.geometry;
    exports pl.edu.pwr.lgawron.lab06.administrator.adminsocket;
    exports pl.edu.pwr.lgawron.lab06.player.executor;
    exports pl.edu.pwr.lgawron.lab06.administrator.queue;
    exports pl.edu.pwr.lgawron.lab06.administrator.adminsocket.models;
    opens pl.edu.pwr.lgawron.lab06.common.input to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.common.frame;
    opens pl.edu.pwr.lgawron.lab06.common.frame to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.player.flow;
    opens pl.edu.pwr.lgawron.lab06.player.flow to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.player.utils;
    opens pl.edu.pwr.lgawron.lab06.player.utils to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.administrator.utils;
    opens pl.edu.pwr.lgawron.lab06.administrator.utils to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.player.playersocket;
    opens pl.edu.pwr.lgawron.lab06.player.playersocket to javafx.fxml;
    opens pl.edu.pwr.lgawron.lab06.common.game.geometry to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.administrator.adminsocket.flow;
}