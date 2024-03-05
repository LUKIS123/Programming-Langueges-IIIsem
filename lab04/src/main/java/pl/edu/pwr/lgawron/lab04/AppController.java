package pl.edu.pwr.lgawron.lab04;

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
    public void simulateSingleRotation() {
        this.parseInputData();
        if (flow == null || (flow instanceof MultipleRotationAnimationFlow && !flow.isFinished())) {
            flow = new SingleRotationAnimationFlow(animationCanvas, valuesHolder, vxChart, vyChart);
            flow.animateCanvas();
        } else if (!flow.isFinished()) {
            flow.animateCanvas();
        }
    }

    @FXML
    public void simulateMultipleRotation() {
        this.parseInputData();
        if (flow == null || flow instanceof SingleRotationAnimationFlow) {
            flow = new MultipleRotationAnimationFlow(animationCanvas, valuesHolder, vxChart, vyChart);
            flow.animateCanvas();
        } else {
            flow.resetValues();
            flow.animateCanvas();
        }
    }

    @FXML
    private void parseInputData() {
        try {
            communicate.setVisible(false);
            valuesHolder.setValues(l1.getText(), l2.getText(), d.getText(), h.getText());
            if (flow instanceof MultipleRotationAnimationFlow) {
                flow.breakTimer();
            }
        } catch (NumberFormatException e) {
            communicate.setText("Cannot run! " + e.getMessage());
            communicate.setVisible(true);
        }
    }

    public void stopAnimation() {
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