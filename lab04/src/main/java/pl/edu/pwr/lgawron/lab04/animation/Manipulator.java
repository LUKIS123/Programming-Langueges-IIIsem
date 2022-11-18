package pl.edu.pwr.lgawron.lab04.animation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

public class Manipulator implements AnimationObject {
    double alpha, beta, gamma;
    private final InputValuesHolder values;
    double hx, dx;

    public Manipulator(InputValuesHolder valuesHolder) {
        this.values = valuesHolder;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        double dx0 = graphicsContext.getCanvas().getWidth() / 2;
        double dy0 = graphicsContext.getCanvas().getHeight() / 2;

        this.drawCoordinateSystem(graphicsContext);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineWidth(2);

        graphicsContext.save();

        graphicsContext.translate(dx0, dy0);
        graphicsContext.scale(1, -1);

        // point ps, (0,0)
        graphicsContext.strokeOval(values.getD() - 2.5, values.getH() - 2.5, 5, 5);
        graphicsContext.fillOval(-3, -3, 6, 6);

        // p0 -> p1
        graphicsContext.rotate(alpha);
        graphicsContext.translate(values.getL1(), 0);
        graphicsContext.strokeLine(0, 0, -values.getL1(), 0);

        // p1 -> p2
        graphicsContext.rotate(-beta);
        graphicsContext.translate(values.getL2(), 0);
        graphicsContext.strokeLine(0, 0, -values.getL2(), 0);

        // point p1,p2
        graphicsContext.strokeOval(-values.getL2() - 2.5, -2.5, 5, 5);
        graphicsContext.fillOval(0, -3, 6, 6);

        graphicsContext.restore();
    }

    @Override
    public void rotate() {
        alpha += 1;
        alpha %= 360;

        hx = Math.sin(Math.toRadians(alpha)) * values.getL1();
        dx = Math.cos(Math.toRadians(alpha)) * values.getL1();
        gamma = Math.toDegrees(Math.atan2((values.getH() - hx), (values.getD() - dx)));

        beta = alpha - gamma;
    }

    private void drawCoordinateSystem(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.BLUE.brighter());
        graphicsContext.setLineWidth(0.2);

        graphicsContext.strokeLine(graphicsContext.getCanvas().getWidth() / 2, -graphicsContext.getCanvas().getHeight(),
                graphicsContext.getCanvas().getWidth() / 2, graphicsContext.getCanvas().getHeight());

        graphicsContext.strokeLine(-graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight() / 2,
                graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight() / 2);
    }

}
