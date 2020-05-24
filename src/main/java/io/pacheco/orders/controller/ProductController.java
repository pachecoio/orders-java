package io.pacheco.orders.controller;

import io.pacheco.orders.model.Product;
import io.pacheco.orders.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/product")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping()
    public @ResponseBody
    Iterable<Product> getAll() {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity create(@RequestBody Product input) {
        Product item = repository.save(input);
        return ResponseEntity.status(201).body(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Product input) {
        if (!repository.findById(id).isPresent()) return ResponseEntity.status(404).body(null);

        input.setId(id);
        Product item = repository.save(input);

        return ResponseEntity.status(200).body(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        if (!repository.findById(id).isPresent()) return ResponseEntity.status(404).body(null);
        repository.deleteById(id);
        return ResponseEntity.status(202).body("Product deleted successfully");
    }

}
