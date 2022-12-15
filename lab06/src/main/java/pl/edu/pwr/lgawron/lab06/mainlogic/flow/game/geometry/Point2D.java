package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry;

public class Point2D {
    private int positionX;
    private int positionY;

    public Point2D(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
