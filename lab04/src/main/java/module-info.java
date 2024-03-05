module pl.edu.pwr.lgawron.lab04 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.base;

    opens pl.edu.pwr.lgawron.lab04 to javafx.fxml, javafx.graphics;
    opens pl.edu.pwr.lgawron.lab04.tools to javafx.fxml, javafx.graphics;
    opens pl.edu.pwr.lgawron.lab04.animation to javafx.fxml, javafx.graphics;
    opens pl.edu.pwr.lgawron.lab04.animation.chart to javafx.fxml, javafx.graphics;
    exports pl.edu.pwr.lgawron.lab04;
    exports pl.edu.pwr.lgawron.lab04.tools;
    exports pl.edu.pwr.lgawron.lab04.animation;
    exports pl.edu.pwr.lgawron.lab04.animation.chart;
}