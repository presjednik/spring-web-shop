package com.bandic.springwebshop.rest;

import com.bandic.springwebshop.model.Customer;
import com.bandic.springwebshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/webshop/customer")
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public Optional<Customer> getById(@PathVariable long id) {
        return customerRepository.findById(id);
    }

    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping
    public Customer saveCustomer(@Valid @RequestBody Customer product) {
        return customerRepository.save(product);
    }

    @PostMapping("/{id}")
    public Customer updateCustomer(@Valid @RequestBody Customer product, @PathVariable long id) {
        if (customerRepository.existsById(id)) {
            product.setId(id);
            return customerRepository.save(product);
        }

        throw new EmptyResultDataAccessException("Customer with id " + id + " doesn't exist.", 0);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@Valid @PathVariable long id) {
        customerRepository.deleteById(id);
        return "Deleted customer with id: " + id;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> exceptionHandler(EmptyResultDataAccessException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}
