package com.bandic.springwebshop.repository;

import com.bandic.springwebshop.model.Customer;
import com.bandic.springwebshop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
