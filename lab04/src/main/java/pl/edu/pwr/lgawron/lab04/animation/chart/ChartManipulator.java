package pl.edu.pwr.lgawron.lab04.animation.chart;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ChartManipulator {
    private final double dotSize;

    public ChartManipulator() {
        this.dotSize = 1;
    }

    public void drawChart(GraphicsContext graphicsContext, double velocityValue, double x) {
        graphicsContext.setStroke(Color.BLACK);
        x = x - 180;
        graphicsContext.fillOval(x - (dotSize / 2), velocityValue - (dotSize / 2), dotSize, dotSize);
    }

    public void setChart(GraphicsContext graphicsContext) {
        double dx0 = graphicsContext.getCanvas().getWidth() / 2;
        double dy0 = graphicsContext.getCanvas().getHeight();

        graphicsContext.translate(dx0, dy0);
        graphicsContext.scale(1, -1);

        graphicsContext.setStroke(Color.BLUE.brighter());
        graphicsContext.setLineWidth(1);
        graphicsContext.strokeLine(-dx0, 0, dx0, 0);
        graphicsContext.strokeLine(-dx0, -dy0, -dx0, dy0);
    }

}
