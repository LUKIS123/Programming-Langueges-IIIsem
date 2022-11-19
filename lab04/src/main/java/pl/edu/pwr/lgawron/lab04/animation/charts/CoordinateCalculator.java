package pl.edu.pwr.lgawron.lab04.animation.charts;

import pl.edu.pwr.lgawron.lab04.tools.InputValuesHolder;

public class CoordinateCalculator {
    private final InputValuesHolder values;

    public CoordinateCalculator(InputValuesHolder values) {
        this.values = values;
    }

    public Point calculateCoordinates(double alpha, double gamma) {

//        double vx = values.getL1() * Math.cos(Math.toRadians(alpha));
//        double ux = values.getL2() * Math.cos(Math.toRadians(gamma));
//        double vy = values.getL1() * Math.sin(Math.toRadians(alpha));
//        double uy = values.getL2() * Math.sin(Math.toRadians(gamma));

        return new Point(values.getL1() * Math.cos(Math.toRadians(alpha)) + values.getL2() * Math.cos(Math.toRadians(gamma)),
                values.getL1() * Math.sin(Math.toRadians(alpha)) + values.getL2() * Math.sin(Math.toRadians(gamma)));
    }

}
