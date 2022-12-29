package pl.edu.pwr.lgawron.lab06.player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab06.common.frame.RegisterPopUp;
import pl.edu.pwr.lgawron.lab06.common.input.InputDataException;
import pl.edu.pwr.lgawron.lab06.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.player.flow.PlayerAppFlow;

public class AppController {
    @FXML
    public VBox controlBox;
    @FXML
    public Pane mainPane;
    @FXML
    public GridPane mapPane;
    @FXML
    public GridPane playerPane;
    @FXML
    public Button startButton;
    @FXML
    public Button take;
    @FXML
    public Button playManuallyButton;
    @FXML
    private Button registerButton;
    private final RegisterPopUp registerPopUp = new RegisterPopUp();
    private final PlayerAppFlow appFlow = new PlayerAppFlow();
    private ValuesHolder values;

    public AppController() {
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent openPopUpEvent) {
        Node node = (Node) openPopUpEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        values = new ValuesHolder();

        registerButton.setOnAction(
                event -> {
                    VBox dialogVbox = registerPopUp.renderPopUp("Please enter server host and port:");

                    TextField server = new TextField();
                    server.setPromptText("Server");
                    server.setText("localhost");
                    TextField port = new TextField();
                    port.setPromptText("Port");
                    port.setText("8085");
                    Button button = new Button("Login");
                    button.setAlignment(Pos.BOTTOM_CENTER);
                    Label communicate = new Label();
                    communicate.setVisible(false);

                    dialogVbox.getChildren().add(server);
                    dialogVbox.getChildren().add(port);
                    dialogVbox.getChildren().add(button);
                    dialogVbox.getChildren().add(communicate);

                    Scene dialogScene = new Scene(dialogVbox, 300, 200);

                    Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(thisStage);
                    dialog.setScene(dialogScene);
                    openPopUpEvent.consume();

                    EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                        try {
                            values.setApplicationArguments(server.getText(), port.getText());
                            inputEvent.consume();

                            // initialization -> joining game
                            appFlow.startRegistration(values, controlBox, mapPane, playerPane);
                            //

                        } catch (InputDataException e) {
                            communicate.setText(e.getMessage() + "!");
                            communicate.setVisible(true);
                        }
                        if (inputEvent.isConsumed()) {
                            dialog.close();
                        }
                    };

                    button.setOnAction(buttonHandler);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
        );
    }

    @FXML
    public void onStartButtonClick() {
        if (values == null) {
            return;
        }
        appFlow.startAlgo();
    }

    @FXML
    public void onPlayManuallyButtonCLick(ActionEvent openControlsEvent) {
        if (values == null) {
            return;
        }
        appFlow.startManualExecutor();

        Node node = (Node) openControlsEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        playManuallyButton.setOnAction(event -> {
            VBox dialogVbox = registerPopUp.renderPopUp("Controls:");

            Button see = new Button("SEE ");
            Button take = new Button("TAKE");
            HBox seeAndTake = new HBox(30);
            seeAndTake.setAlignment(Pos.TOP_CENTER);

            EventHandler<ActionEvent> takeTreasure = takeEvent -> appFlow.take();
            take.setOnAction(takeTreasure);
            EventHandler<ActionEvent> seeEnv = seeEvent -> appFlow.see();

            see.setOnAction(seeEnv);
            seeAndTake.getChildren().add(see);
            seeAndTake.getChildren().add(take);
            dialogVbox.getChildren().add(seeAndTake);

            Button up = new Button("↑");
            Button left_up = new Button("↖");
            Button right_up = new Button("↗");
            HBox upper = new HBox();
            upper.setAlignment(Pos.CENTER);

            EventHandler<ActionEvent> moveUp = moveEvent -> appFlow.moveUp(0);
            up.setOnAction(moveUp);
            EventHandler<ActionEvent> moveLeftUp = moveEvent -> appFlow.moveUp(-1);
            left_up.setOnAction(moveLeftUp);
            EventHandler<ActionEvent> moveRightUp = moveEvent -> appFlow.moveUp(1);
            right_up.setOnAction(moveRightUp);

            upper.getChildren().add(left_up);
            upper.getChildren().add(up);
            upper.getChildren().add(right_up);
            dialogVbox.getChildren().add(upper);

            Button left = new Button("←");
            Button right = new Button("→");
            HBox level = new HBox();
            level.setAlignment(Pos.CENTER);

            EventHandler<ActionEvent> moveLeft = moveEvent -> appFlow.moveLeft();
            left.setOnAction(moveLeft);
            EventHandler<ActionEvent> moveRight = moveEvent -> appFlow.moveRight();
            right.setOnAction(moveRight);

            level.getChildren().add(left);
            level.getChildren().add(right);
            dialogVbox.getChildren().add(level);

            Button down = new Button("↓");
            Button left_down = new Button("↙");
            Button right_down = new Button("↘");
            HBox lower = new HBox();
            lower.setAlignment(Pos.CENTER);

            EventHandler<ActionEvent> moveDown = moveEvent -> appFlow.moveDown(0);
            down.setOnAction(moveDown);
            EventHandler<ActionEvent> moveLeftDown = moveEvent -> appFlow.moveDown(-1);
            left_down.setOnAction(moveLeftDown);
            EventHandler<ActionEvent> moveRightDown = moveEvent -> appFlow.moveDown(1);
            right_down.setOnAction(moveRightDown);

            lower.getChildren().add(left_down);
            lower.getChildren().add(down);
            lower.getChildren().add(right_down);
            dialogVbox.getChildren().add(lower);

            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(thisStage);
            dialog.setScene(dialogScene);

            dialog.setScene(dialogScene);
            dialog.show();
        });
    }

    public void onExitApplication() {
        appFlow.killApp();
    }

}