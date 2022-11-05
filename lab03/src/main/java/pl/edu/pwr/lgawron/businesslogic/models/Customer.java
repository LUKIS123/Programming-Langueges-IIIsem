package pl.edu.pwr.lgawron.businesslogic.models;

public class Customer {
    private int id;
    public String username;

    public Customer() {
    }

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
