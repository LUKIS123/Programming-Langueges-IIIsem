package pl.edu.pwr.lgawron.lab04;

public class ParseValues {
    private int l1;
    private int l2;
    private int d;
    private int h;

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

    public void setL1(String l1) {
        this.l1 = Integer.parseInt(l1);
    }

    public void setL2(String l2) {
        this.l2 = Integer.parseInt(l2);
    }

    public void setD(String d) {
        this.d = Integer.parseInt(d);
    }

    public void setH(String h) {
        this.h = Integer.parseInt(h);
    }
}
