package com.bandic.springwebshop.service;

import com.bandic.springwebshop.exception.OrderItemNotFoundException;
import com.bandic.springwebshop.exception.ProductNotAvailableException;
import com.bandic.springwebshop.exception.ProductNotFoundException;
import com.bandic.springwebshop.model.OrderItem;
import com.bandic.springwebshop.model.Product;
import com.bandic.springwebshop.model.WebshopOrder;
import com.bandic.springwebshop.repository.OrderItemRepository;
import com.bandic.springwebshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public Iterable<OrderItem> findItemsByOrderId(long orderId) {
        return orderItemRepository.findByWebshopOrderId(orderId);
    }

    public void addOrderItem(WebshopOrder order, long productId, int quantity) {
        productRepository.findById(productId)
                .ifPresentOrElse(
                        product -> saveOrderItemIfProductIsAvailable(order, product, quantity),
                        () -> {
                            throw new ProductNotFoundException(productId);
                        }
                );
    }

    public void removeOrderItem(long orderItemId) {
        orderItemRepository.findById(orderItemId)
                .ifPresentOrElse(this::deleteOrderItem,
                        () -> {
                            throw new OrderItemNotFoundException(orderItemId);
                        });
    }

    private void saveOrderItemIfProductIsAvailable(WebshopOrder order, Product product, int quantity) {
        if (!product.getIsAvailable()) {
            throw new ProductNotAvailableException(product.getId());
        }

        orderItemRepository.save(createOrderItem(order, product, quantity));
    }

    private void deleteOrderItem(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    private OrderItem createOrderItem(WebshopOrder order, Product product, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setWebshopOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        return orderItem;
    }
}

