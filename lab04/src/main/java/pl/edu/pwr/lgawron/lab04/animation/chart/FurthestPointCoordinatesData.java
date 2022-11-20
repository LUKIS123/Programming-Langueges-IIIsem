package pl.edu.pwr.lgawron.lab04.animation.chart;

import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

import java.util.ArrayList;
import java.util.List;

public class FurthestPointCoordinatesData {
    private final CoordinateCalculator coordinateCalculator;
    private final List<Point> pointCoordinates;
    private Thread t;

    public FurthestPointCoordinatesData(InputValuesHolder valuesHolder) {
        this.pointCoordinates = new ArrayList<>();
        this.coordinateCalculator = new CoordinateCalculator(valuesHolder);
    }

    public void addPoint(double alpha, double gamma) {
        pointCoordinates.add(coordinateCalculator.calculateCoordinates(alpha, gamma));
    }

    public List<Point> getPointCoordinates() {
        return pointCoordinates;
    }

    public double calculateDisplacementX(int i) {
        if (i == 359) {
            return Math.sqrt(Math.pow(pointCoordinates.get(359).x() - pointCoordinates.get(0).x(), 2));
        }
        return Math.sqrt(Math.pow(pointCoordinates.get(i).x() - pointCoordinates.get(i + 1).x(), 2));
    }

    public double calculateDisplacementY(int i) {
        if (i == 359) {
            return Math.sqrt(Math.pow(pointCoordinates.get(359).y() - pointCoordinates.get(0).y(), 2));
        }
        return Math.sqrt(Math.pow(pointCoordinates.get(i).y() - pointCoordinates.get(i + 1).y(), 2));
    }

    public void clearDataAfterRotation() {
        if (pointCoordinates.size() >= 360) {
            pointCoordinates.clear();
        }
    }

    public void dataCleaner() {
        if (t != null) {
            if (t.isAlive())
                return;
        }
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (pointCoordinates.size() >= 720) {
                    pointCoordinates.clear();
                    t.interrupt();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }

}
