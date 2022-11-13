package pl.edu.pwr.lgawron.lab04;

public class InputValuesHolder {
    private final int l1;
    private final int l2;
    private final int d;
    private final int h;

    public InputValuesHolder(String l1, String l2, String d, String h) {
        this.l1 = Integer.parseInt(l1);
        this.l2 = Integer.parseInt(l2);
        this.d = Integer.parseInt(d);
        this.h = Integer.parseInt(h);
    }

    public int getL1() {
        return l1;
    }

    public int getL2() {
        return l2;
    }

    public int getD() {
        return d;
    }

    public int getH() {
        return h;
    }
}
