package pl.edu.pwr.lgawron.lab04.animation;

import javafx.scene.canvas.Canvas;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

public class AnimationFlow {
    private final Canvas canvas;
    private final AnimationObject manipulator;
    private MachineAnimationTimer timer;

    public AnimationFlow(Canvas canvas, InputValuesHolder valuesHolder) {
        this.canvas = canvas;
        this.manipulator = new Manipulator(valuesHolder);
    }

    public void animateCanvas() {
        timer = new MachineAnimationTimer() {
            int i = 360;

            @Override
            public void handle(long now) {
                canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                manipulator.rotate();
                manipulator.draw(canvas.getGraphicsContext2D());
                i--;
                if (i == 0) {
                    stop();
                }
            }
        };
        timer.start();
    }

    public void breakTimer() {
        timer.stop();
    }

    public boolean isFinished() {
        return timer.isRunning();
    }
}
