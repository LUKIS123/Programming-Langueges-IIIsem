package pl.edu.pwr.lgawron.lab04.animation;

import javafx.scene.canvas.Canvas;
import pl.edu.pwr.lgawron.lab04.animation.charts.FurthestPointCoordinatesData;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

public class MultipleRotationAnimationFlow implements AnimationFlow {
    private final Canvas canvas;
    private final AnimationObject manipulator;
    private CustomAnimationTimer timer;
    private FurthestPointCoordinatesData furthestPointCoordinatesData;

    public MultipleRotationAnimationFlow(Canvas canvas, InputValuesHolder valuesHolder) {
        this.canvas = canvas;
        this.furthestPointCoordinatesData = new FurthestPointCoordinatesData(valuesHolder);
        this.manipulator = new Manipulator(valuesHolder, furthestPointCoordinatesData);
    }

    @Override
    public void animateCanvas() {
        timer = new CustomAnimationTimer(10) {
            @Override
            public void handle() {
                canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                manipulator.rotate();
                manipulator.draw(canvas.getGraphicsContext2D());
            }
        };
        timer.start();
    }

    @Override
    public void breakTimer() {
        timer.stop();
    }

    @Override
    public boolean isFinished() {
        return timer.isRunning();
    }
}
