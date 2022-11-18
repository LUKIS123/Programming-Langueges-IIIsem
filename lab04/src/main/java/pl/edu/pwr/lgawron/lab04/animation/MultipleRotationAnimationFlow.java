package pl.edu.pwr.lgawron.lab04.animation;

import javafx.scene.canvas.Canvas;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

public class MultipleRotationAnimationFlow implements Animation {
    private final Canvas canvas;
    private final AnimationObject manipulator;
    private MachineAnimationTimer timer;

    public MultipleRotationAnimationFlow(Canvas canvas, InputValuesHolder valuesHolder) {
        this.canvas = canvas;
        this.manipulator = new Manipulator(valuesHolder);
    }

    @Override
    public void animateCanvas() {
        timer = new MachineAnimationTimer() {
            @Override
            public void handle(long now) {
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
