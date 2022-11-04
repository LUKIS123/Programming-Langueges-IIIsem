package pl.edu.pwr.lgawron.businesslogic.services;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService implements ModelService<Product> {
    private final ProductRepository repository;
    private final List<Product> products;

    public ProductService() {
        this.repository = new ProductRepository();
        this.products = new ArrayList<>();
        this.refreshDataList();
    }

    @Override
    public List<Product> refreshDataList() {
        List<Product> loadData = repository.loadData();
        if (loadData == null) {
            return products;
        }
        if (products.isEmpty()) {
            products.addAll(loadData);
        } else {
            for (Product product : loadData) {
                Product byId = this.findById(product.getId());
                if (byId == null) {
                    products.add(product);
                } else {
                    products.set(products.indexOf(byId), product);
                }
            }
        }
        return products;
    }

    @Override
    public List<Product> getDataList() {
        return this.refreshDataList();
    }

    @Override
    public Product findById(int id) {
        Optional<Product> first = products.stream().filter(product -> product.getId() == id).findFirst();
        return first.orElse(null);
    }

    @Override
    public void addToDatabase(Product product) {
        this.products.add(product);
        this.repository.saveData(products);
    }

    @Override
    public void deleteFromDatabase(int id) {
        Product byId = this.findById(id);
        if (byId != null) {
            this.products.remove(byId);
            this.repository.saveData(products);
        }
    }
}
