package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements ModelRepository<Customer> {
    private final List<Customer> customerList;

    public CustomerRepository() {
        this.customerList = new ArrayList<>();

        this.customerList.add(new Customer(1, "Lukas"));
        this.customerList.add(new Customer(2, "Tom"));
        this.customerList.add(new Customer(3, "Michael"));
    }

    @Override
    public void loadData(List<Customer> loadedList) {
        if (customerList.isEmpty()) {
            customerList.addAll(loadedList);
        } else {
            for (Customer customer : loadedList) {
                Customer byId = this.findById(customer.getId());
                if (byId == null) {
                    customerList.add(customer);
                }
            }
        }
    }

    @Override
    public List<Customer> getDataList() {
        return customerList;
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> first = customerList.stream().filter(customer -> customer.getId() == id).findFirst();
        return first.orElse(null);
    }

}
