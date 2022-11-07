package pl.edu.pwr.lgawron.businesslogic.services;

import pl.edu.pwr.lgawron.businesslogic.models.Customer;
import pl.edu.pwr.lgawron.businesslogic.repositories.CustomerRepository;
import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerService implements ModelService<Customer> {
    private final CustomerRepository repository;
    private final List<Customer> customers;

    public CustomerService() {
        this.repository = new CustomerRepository();
        this.customers = new ArrayList<>();
        this.refreshDataList();
    }

    @Override
    public List<Customer> refreshDataList() {
        List<Customer> loadedData = repository.loadData();
        if (loadedData == null) {
            return customers;
        }
        if (customers.isEmpty()) {
            customers.addAll(loadedData);
        } else {
            for (Customer customer : loadedData) {
                Customer byId = this.findById(customer.getId());
                if (byId == null) {
                    customers.add(customer);
                } else {
                    customers.set(customers.indexOf(byId), customer);
                }
            }
        }
        return customers;
    }

    @Override
    public List<Customer> getDataList() {
        return this.refreshDataList();
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> first = customers.stream().filter(customer -> customer.getId() == id).findFirst();
        return first.orElse(null);
    }

    @Override
    public void addToDatabase(Customer customer) throws DatabaseSaveException {
        this.customers.add(customer);
        this.repository.saveData(customers);
    }

    @Override
    public void deleteFromDatabase(int id) throws DatabaseSaveException {
        Customer byId = this.findById(id);
        if (byId != null) {
            customers.remove(byId);
            this.repository.saveData(customers);
        }
    }
}
