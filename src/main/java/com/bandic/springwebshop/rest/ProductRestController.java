package com.bandic.springwebshop.rest;

import com.bandic.springwebshop.model.Product;
import com.bandic.springwebshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/webshop/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductRepository productRepository;

    @GetMapping("/{id}")
    public Product getById(@PathVariable long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Product with id " + id + " doesn't exist.", 0));
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product saveProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @PostMapping("/{id}")
    public Product updateProduct(@Valid @RequestBody Product product, @PathVariable long id) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }

        throw new EmptyResultDataAccessException("Product with id " + id + " doesn't exist.", 0);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@Valid @PathVariable long id) {
        productRepository.deleteById(id);
        return "Deleted product with id: " + id;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> exceptionHandler(EmptyResultDataAccessException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}
