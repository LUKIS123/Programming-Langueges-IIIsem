package pl.edu.pwr.lgawron.lab04.animation;

public interface AnimationFlow {
    void animateCanvas();

    void breakTimer();

    boolean isFinished();

    void resetValues();
}
