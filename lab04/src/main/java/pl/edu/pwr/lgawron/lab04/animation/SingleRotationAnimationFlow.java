package pl.edu.pwr.lgawron.lab04.animation;

import javafx.scene.canvas.Canvas;
import pl.edu.pwr.lgawron.lab04.animation.charts.ChartManipulator;
import pl.edu.pwr.lgawron.lab04.animation.charts.FurthestPointCoordinatesData;
import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

public class SingleRotationAnimationFlow implements AnimationFlow {
    private final Canvas animationCanvas;
    private final AnimationObject manipulator;
    private CustomAnimationTimer MachineAnimationTimer;
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
        MachineAnimationTimer = new CustomAnimationTimer(10) {
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
        MachineAnimationTimer.start();
    }

    public void drawVxChart() {
        vxChart.getGraphicsContext2D().save();
        vxChart.getGraphicsContext2D().clearRect(0, 0, vxChart.getWidth(), vxChart.getHeight());

        ChartManipulator chartManipulator = new ChartManipulator(furthestPointCoordinatesData, vxChart);
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
        vyChart.getGraphicsContext2D().clearRect(-vyChart.getWidth(), 0, vyChart.getWidth(), vyChart.getHeight());

        ChartManipulator chartManipulator = new ChartManipulator(furthestPointCoordinatesData, vyChart);
        chartManipulator.setChart(vyChart.getGraphicsContext2D());
        vyTimer = new CustomAnimationTimer(10) {
            int i = 0;

            @Override
            public void handle() {
                chartManipulator.drawChart(vyChart.getGraphicsContext2D(), furthestPointCoordinatesData.calculateDisplacementY(i) * 100, i);
                i++;
                if (i == 359) {
                    stop();
                }
            }
        };
        vyTimer.start();
    }

    @Override
    public void breakTimer() {
        MachineAnimationTimer.stop();
        manipulator.reset(animationCanvas.getGraphicsContext2D());
        vxTimer.stop();
        vyTimer.stop();
    }

    @Override
    public boolean isFinished() {
        return MachineAnimationTimer.isRunning();
    }
}
