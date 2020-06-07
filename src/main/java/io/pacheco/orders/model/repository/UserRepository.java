package io.pacheco.orders.model.repository;

import io.pacheco.orders.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    Iterable<User> findByRole(String role);

}
