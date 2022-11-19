package pl.edu.pwr.lgawron.lab04.animation;

import javafx.scene.canvas.Canvas;
import pl.edu.pwr.lgawron.lab04.animation.chart.ChartManipulator;
import pl.edu.pwr.lgawron.lab04.animation.chart.FurthestPointCoordinatesData;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

public class SingleRotationAnimationFlow implements AnimationFlow {
    private final Canvas animationCanvas;
    private final AnimationObject manipulator;
    private CustomAnimationTimer machineAnimationTimer;
    private final FurthestPointCoordinatesData furthestPointCoordinatesData;
    private final Canvas vxChart;
    private final Canvas vyChart;
    private CustomAnimationTimer vxTimer;
    private CustomAnimationTimer vyTimer;

    public SingleRotationAnimationFlow(Canvas canvas, InputValuesHolder valuesHolder, Canvas vxChart, Canvas vyChart) {
        this.animationCanvas = canvas;
        this.furthestPointCoordinatesData = new FurthestPointCoordinatesData(valuesHolder);
        this.manipulator = new Manipulator(valuesHolder, furthestPointCoordinatesData);
        this.vxChart = vxChart;
        this.vyChart = vyChart;
    }

    @Override
    public void animateCanvas() {
        furthestPointCoordinatesData.clearDataAfterRotation();
        machineAnimationTimer = new CustomAnimationTimer(10) {
            int i = 360;

            @Override
            public void handle() {
                animationCanvas.getGraphicsContext2D().clearRect(0, 0, animationCanvas.getWidth(), animationCanvas.getHeight());
                manipulator.rotate();
                manipulator.draw(animationCanvas.getGraphicsContext2D());
                i--;
                if (i == 0) {
                    stop();
                    drawVxChart();
                    drawVyChart();
                }
            }
        };
        machineAnimationTimer.start();
    }

    public void drawVxChart() {
        vxChart.getGraphicsContext2D().save();
        vxChart.getGraphicsContext2D().clearRect(0, 0, vxChart.getWidth(), vxChart.getHeight());

        ChartManipulator chartManipulator = new ChartManipulator();
        chartManipulator.setChart(vxChart.getGraphicsContext2D());
        vxTimer = new CustomAnimationTimer(10) {
            int i = 0;

            @Override
            public void handle() {
                chartManipulator.drawChart(vxChart.getGraphicsContext2D(), furthestPointCoordinatesData.calculateDisplacementX(i) * 100, i);
                i++;
                if (i == 359) {
                    stop();
                    vxChart.getGraphicsContext2D().restore();
                    vxChart.getGraphicsContext2D().save();
                }
            }
        };
        vxTimer.start();
    }

    public void drawVyChart() {
        vyChart.getGraphicsContext2D().save();
        vyChart.getGraphicsContext2D().clearRect(0, 0, vyChart.getWidth(), vyChart.getHeight());

        ChartManipulator chartManipulator = new ChartManipulator();
        chartManipulator.setChart(vyChart.getGraphicsContext2D());
        vyTimer = new CustomAnimationTimer(10) {
            int i = 0;

            @Override
            public void handle() {
                chartManipulator.drawChart(vyChart.getGraphicsContext2D(), furthestPointCoordinatesData.calculateDisplacementY(i) * 100, i);
                i++;
                if (i == 359) {
                    stop();
                    vyChart.getGraphicsContext2D().restore();
                    vyChart.getGraphicsContext2D().save();
                }
            }
        };
        vyTimer.start();
    }

    @Override
    public void breakTimer() {
        machineAnimationTimer.stop();
        manipulator.reset(animationCanvas.getGraphicsContext2D());

        vxTimer.stop();
        vyTimer.stop();
        vxChart.getGraphicsContext2D().restore();
        vxChart.getGraphicsContext2D().clearRect(0, 0, vxChart.getWidth(), vxChart.getHeight());
        vyChart.getGraphicsContext2D().restore();
        vyChart.getGraphicsContext2D().clearRect(0, 0, vyChart.getWidth(), vyChart.getHeight());
    }

    @Override
    public boolean isFinished() {
        return machineAnimationTimer.isRunning();
    }

    @Override
    public void resetValues() {
    }
}
