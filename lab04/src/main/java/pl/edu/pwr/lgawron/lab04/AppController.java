package pl.edu.pwr.lgawron.lab04;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.edu.pwr.lgawron.lab04.animation.AnimationFlow;
import pl.edu.pwr.lgawron.lab04.animation.MultipleRotationAnimationFlow;
import pl.edu.pwr.lgawron.lab04.animation.SingleRotationAnimationFlow;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

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
    private Canvas animationCanvas;
    @FXML
    private Canvas vxChart;
    @FXML
    private Canvas vyChart;
    private final InputValuesHolder valuesHolder = new InputValuesHolder();
    private AnimationFlow flow;

    public AppController() {
    }

    @FXML
    public void simulateSingleRotation(ActionEvent actionEvent) {
        this.parseInputData();
        if (flow == null || (flow instanceof MultipleRotationAnimationFlow && !flow.isFinished())) {
            flow = new SingleRotationAnimationFlow(animationCanvas, valuesHolder, vxChart, vyChart);
            flow.animateCanvas();
        } else if (!flow.isFinished()) {
            flow.animateCanvas();
        }
        // mozna sprawdzac czy jest consumed, wtedy wyswitlic okno z wykresem czy cos
        //actionEvent.consume();
    }

    @FXML
    public void simulateMultipleRotation(ActionEvent actionEvent) {
        this.parseInputData();
        if (flow == null || flow instanceof SingleRotationAnimationFlow) {
            flow = new MultipleRotationAnimationFlow(animationCanvas, valuesHolder);
            flow.animateCanvas();
        } else if (!flow.isFinished()) {
            flow.animateCanvas();
        }
    }

    @FXML
    private void parseInputData() {
        try {
            communicate.setVisible(false);
            valuesHolder.setValues(l1.getText(), l2.getText(), d.getText(), h.getText());
        } catch (NumberFormatException e) {
            communicate.setText("Cannot run! " + e.getMessage());
            communicate.setVisible(true);
        }
    }

    public void stopAnimation(ActionEvent actionEvent) {
        if (flow != null) {
            flow.breakTimer();
        }
    }

    public void onExitApplication() {
        if (flow != null) {
            flow.breakTimer();
        }
    }
}