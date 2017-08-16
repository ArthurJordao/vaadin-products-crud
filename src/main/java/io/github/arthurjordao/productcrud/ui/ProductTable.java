package io.github.arthurjordao.productcrud.ui;

import io.github.arthurjordao.productcrud.model.Product;
import io.github.arthurjordao.productcrud.repository.ProductRepository;

public interface ProductTable {
    void updateProducts();

    void addProduct(Product product);

    void removeProduct(Product product);

    ProductRepository getProductRepository();
}
