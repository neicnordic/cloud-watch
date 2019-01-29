package no.neic.cloudwatch.backend;

import java.io.Serializable;
import java.util.Collection;

import no.neic.cloudwatch.backend.data.Category;
import no.neic.cloudwatch.backend.data.Product;
import no.neic.cloudwatch.backend.mock.MockDataService;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    public abstract Collection<Product> getAllProducts();

    public abstract Collection<Category> getAllCategories();

    public abstract void updateProduct(Product p);

    public abstract void deleteProduct(int productId);

    public abstract Product getProductById(int productId);

    public static DataService get() {
        return MockDataService.getInstance();
    }

}
