package pl.edu.pwr.lgawron.lab06.lab06;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class HelloController {
    public HBox mainBox;
    public MenuButton registerButton;

    @FXML
    protected void onRegisterButtonClick(ActionEvent actionEvent) {
        final MenuItem wizPopup = new MenuItem();
        wizPopup.setGraphic(RegisterPopUp.createPopupContent());
        registerButton.getItems().setAll(wizPopup);

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                registerButton.show();
            }
        });

    }

    public void onExitApplication() {

    }
}