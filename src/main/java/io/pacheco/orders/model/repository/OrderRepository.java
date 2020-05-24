package io.pacheco.orders.model.repository;

import io.pacheco.orders.model.Order;
import io.pacheco.orders.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}
