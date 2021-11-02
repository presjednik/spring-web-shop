package com.bandic.springwebshop.service;

import com.bandic.springwebshop.exception.ProductNotAvailableException;
import com.bandic.springwebshop.exception.ProductNotFoundException;
import com.bandic.springwebshop.model.OrderItem;
import com.bandic.springwebshop.model.Product;
import com.bandic.springwebshop.model.WebshopOrder;
import com.bandic.springwebshop.repository.OrderItemRepository;
import com.bandic.springwebshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderItemServiceTest {
    @MockBean private ProductRepository productRepository;
    @MockBean private OrderItemRepository orderItemRepository;
    private OrderItemService orderItemService;

    @PostConstruct
    public void init() {
        orderItemService = new OrderItemService(orderItemRepository, productRepository);
    }

    @Test
    public void whenNoProductInDBForIdExceptionIsThrown() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> orderItemService.addOrderItem(null, productId, 0)
        );
    }

    @Test
    public void whenNonAvailableProductInDBForIdNotAvailableExceptionIsThrown() {
        long productId = 2L;
        Product product = new Product();
        product.setIsAvailable(false);

        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        assertThrows(
                ProductNotAvailableException.class,
                () -> orderItemService.addOrderItem(null, productId, 0)
        );
    }

    @Test
    public void whenAvailableProductInDBForIdItemIsSaved() {
        long productId = 3L;
        int quantity = 10;

        Product product = new Product();
        product.setId(productId);
        product.setIsAvailable(true);

        WebshopOrder order = new WebshopOrder();

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setWebshopOrder(order);
        orderItem.setQuantity(quantity);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        orderItemService.addOrderItem(order, productId, quantity);

        verify(orderItemRepository).save(any());
    }
}
