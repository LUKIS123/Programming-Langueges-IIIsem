package pl.edu.pwr.lgawron.lab04.tools;

public class InputValuesHolder {
    private int l1;
    private int l2;
    private int d;
    private int h;

    public InputValuesHolder() {
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

    public void setValues(String l1, String l2, String d, String h) {
        this.l1 = Integer.parseInt(l1);
        this.l2 = Integer.parseInt(l2);
        this.d = Integer.parseInt(d);
        this.h = Integer.parseInt(h);
    }
}
