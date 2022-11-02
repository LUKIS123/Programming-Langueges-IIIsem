package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository implements ModelRepository<Product> {
    private final List<Product> productList;

    public ProductRepository() {
        this.productList = new ArrayList<>();

        this.productList.add(new Product(1, 1, "Drill"));
        this.productList.add(new Product(2, 2, "Chainsaw"));
        this.productList.add(new Product(3, 2, "Lawnmower"));
    }

    @Override
    public void loadData(List<Product> loadedList) {
        if (productList.isEmpty()) {
            productList.addAll(loadedList);
        } else {
            for (Product product : loadedList) {
                Product byId = this.findById(product.getId());
                if (byId == null) {
                    productList.add(product);
                }
            }
        }

    }

    @Override
    public List<Product> getDataList() {
        return productList;
    }

    @Override
    public Product findById(int id) {
        Optional<Product> first = productList.stream().filter(product -> product.getId() == id).findFirst();
        return first.orElse(null);
    }
}
