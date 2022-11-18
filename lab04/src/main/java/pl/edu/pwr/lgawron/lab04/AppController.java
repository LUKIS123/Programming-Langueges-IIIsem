package pl.edu.pwr.lgawron.lab04;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.edu.pwr.lgawron.lab04.animation.AnimationFlow;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

// klasa przyjmuje elementy na widoku, ustawia je
public class AppController {
    @FXML
    private TextField l1;
    @FXML
    private TextField l2;
    @FXML
    private TextField d;
    @FXML
    private TextField h;
    @FXML
    private Label communicate;
    @FXML
    private Canvas canvas;
    private final InputValuesHolder valuesHolder = new InputValuesHolder();
    private AnimationFlow flow;

    public AppController() {
    }

    @FXML
    public void simuateSingleRotation(ActionEvent actionEvent) {
        try {
            communicate.setVisible(false);
            valuesHolder.setValues(l1.getText(), l2.getText(), d.getText(), h.getText());

            if (flow == null) {
                flow = new AnimationFlow(canvas, valuesHolder);
                flow.animateCanvas();
            } else if (!flow.isFinished()) {
                flow.animateCanvas();
            }

            // mozna sprawdzac czy jest consumed, wtedy wyswitlic okno z wykresem czy cos
            //actionEvent.consume();

        } catch (NumberFormatException e) {
            communicate.setText("Cannot run! " + e.getMessage());
            communicate.setVisible(true);
        }
    }

    public void onExitApplication() {
        if (flow != null) {
            flow.breakTimer();
        }
    }

}