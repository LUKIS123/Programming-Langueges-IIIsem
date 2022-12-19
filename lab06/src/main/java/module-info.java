module pl.edu.pwr.lgawron.lab06 {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.edu.pwr.lgawron.lab06.administrator to javafx.fxml;
    opens pl.edu.pwr.lgawron.lab06.player to javafx.fxml;

    exports pl.edu.pwr.lgawron.lab06.administrator;
    exports pl.edu.pwr.lgawron.lab06.player;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.parse;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket;
    exports pl.edu.pwr.lgawron.lab06.player.ai;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.flow.game;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.flow;
    opens pl.edu.pwr.lgawron.lab06.mainlogic.parse to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.frame;
    opens pl.edu.pwr.lgawron.lab06.mainlogic.frame to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.player.flow;
    opens pl.edu.pwr.lgawron.lab06.player.flow to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.player.utils;
    opens pl.edu.pwr.lgawron.lab06.player.utils to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.administrator.utils;
    opens pl.edu.pwr.lgawron.lab06.administrator.utils to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.player.playersocket;
    opens pl.edu.pwr.lgawron.lab06.player.playersocket to javafx.fxml;
    exports pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry;
    opens pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry to javafx.fxml;
}