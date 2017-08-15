package io.github.arthurjordao.productcrud.ui;

import io.github.arthurjordao.productcrud.model.Product;

public interface ProductList {
    void addProduct(Product product);

    void remove(Product product);
}
