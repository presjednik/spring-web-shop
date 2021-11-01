package com.bandic.springwebshop.repository;

import com.bandic.springwebshop.model.WebshopOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebshopOrderRepository extends CrudRepository<WebshopOrder, Long> {
}
