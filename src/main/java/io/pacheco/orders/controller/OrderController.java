package io.pacheco.orders.controller;

import io.pacheco.orders.model.Order;
import io.pacheco.orders.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/order")
public class OrderController {
    @Autowired
    private OrderRepository repository;

    @GetMapping()
    public @ResponseBody
    Iterable<Order> getAll() {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity create(@RequestBody Order input) {
        Order item = repository.save(input);
        return ResponseEntity.status(201).body(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Order input) {
        if (!repository.findById(id).isPresent()) return ResponseEntity.status(404).body(null);

        input.setId(id);
        Order item = repository.save(input);

        return ResponseEntity.status(200).body(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        if (!repository.findById(id).isPresent()) return ResponseEntity.status(404).body(null);
        try {
            repository.deleteById(id);
            return ResponseEntity.status(202).body("Order deleted successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
