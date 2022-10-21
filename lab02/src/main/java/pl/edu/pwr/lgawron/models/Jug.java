package pl.edu.pwr.lgawron.models;

public class Jug {
    private final int id;
    private final int flavourId;
    private int volume;
    private final int volumeInfo;

    public Jug(int id, int flavourId, int volume) {
        this.id = id;
        this.flavourId = flavourId;
        this.volume = volume;
        this.volumeInfo = volume;
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

    public int getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Jug getJugCopy() {
        return new Jug(id, flavourId, volumeInfo);
    }

    public void reset() {
        this.volume = volumeInfo;
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
