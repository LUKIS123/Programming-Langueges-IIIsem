package pl.edu.pwr.lgawron.models;

public class Jug {
    private final int id;
    private final int flavourId;
    private int volume;

    public Jug(int id, int flavourId, int volume) {
        this.id = id;
        this.flavourId = flavourId;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public int getFlavourId() {
        return flavourId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void pourDrink(Jug jug) {
        volume -= 100;
    }

    @Override
    public String toString() {
        return "Jug{" +
                "id=" + id +
                ", flavourId=" + flavourId +
                ", volume=" + volume +
                '}';
    }
}
