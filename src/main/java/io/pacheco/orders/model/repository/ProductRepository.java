package io.pacheco.orders.model.repository;

import io.pacheco.orders.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
