package io.pacheco.orders.controller;

import io.pacheco.orders.model.Product;
import io.pacheco.orders.model.repository.ProductRepository;
import io.pacheco.orders.services.FileService;
import io.pacheco.orders.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path="/api/product")
public class ProductController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductRepository repository;

    @GetMapping()
    public @ResponseBody
    Iterable<Product> getAll() {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
    @ResponseBody
    public ResponseEntity create(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("image") MultipartFile image
            ) {

        String imageUrl = fileService.storeFile(image);

        Product product = repository.save(
                new Product(
                        name,
                        description,
                        price,
                        imageUrl
                )
        );
        return ResponseEntity.status(201).body(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Integer id,
                                 @RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("price") double price,
                                 @RequestParam("image") MultipartFile image) {
        if (!repository.findById(id).isPresent()) return ResponseEntity.status(404).body(null);

        String imageUrl = fileService.storeFile(image);

        Product item = repository.save(
                new Product(
                        id,
                        name,
                        description,
                        price,
                        imageUrl
                )
        );

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
