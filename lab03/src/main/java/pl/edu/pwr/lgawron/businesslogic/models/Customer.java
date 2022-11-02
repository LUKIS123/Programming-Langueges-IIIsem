package pl.edu.pwr.lgawron.businesslogic.models;

public class Customer {
    private final int id;
    public final String username;

    public Customer(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
