package com.bandic.springwebshop.repository;

import com.bandic.springwebshop.model.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    Iterable<OrderItem> findByWebshopOrderId(long orderId);
}
