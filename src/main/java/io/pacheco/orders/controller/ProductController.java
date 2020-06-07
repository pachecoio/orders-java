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

import java.util.Optional;

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
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "description", required = false) String description,
                                 @RequestParam(value = "price") double price,
                                 @RequestParam(value = "image", required = false) MultipartFile image) {

        Optional<Product> currentProduct = repository.findById(id);
        if (!currentProduct.isPresent()) return ResponseEntity.status(404).body(null);

        Product product = currentProduct.get();

        if(image != null) {
            String imageUrl = fileService.storeFile(image);
            product.setImage(imageUrl);
        }

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        Product item = repository.save(product);

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
