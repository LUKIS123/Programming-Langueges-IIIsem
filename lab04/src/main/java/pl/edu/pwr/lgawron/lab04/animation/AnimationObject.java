package pl.edu.pwr.lgawron.lab04.animation;

import javafx.scene.canvas.GraphicsContext;

public interface AnimationObject {
    public void draw(GraphicsContext graphicsContext);

    public void rotate();

    public void reset(GraphicsContext graphicsContext);
}
