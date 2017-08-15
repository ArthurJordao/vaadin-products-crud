package io.github.arthurjordao.productcrud.repository;

import io.github.arthurjordao.productcrud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
