package com.bandic.springwebshop.rest;

import com.bandic.springwebshop.exception.OrderNotFoundException;
import com.bandic.springwebshop.model.OrderItem;
import com.bandic.springwebshop.model.WebshopOrder;
import com.bandic.springwebshop.model.dto.ProductRequestDto;
import com.bandic.springwebshop.service.OrderItemService;
import com.bandic.springwebshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/webshop/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping
    public Iterable<WebshopOrder> getAllWebshopOrders() {
        return orderService.finalAllOrders();
    }

    @GetMapping("/{orderId}")
    public Optional<WebshopOrder> getById(@PathVariable long orderId) {
        return orderService.findOrderById(orderId);
    }

    @GetMapping("/{orderId}/items")
    public Iterable<OrderItem> getOrderItems(@PathVariable long orderId) {
        return orderItemService.findItemsByOrderId(orderId);
    }

    @DeleteMapping("/{orderId}")
    public String deleteWebshopOrder(@PathVariable long orderId) {
        orderService.deleteOrderById(orderId);
        return "Deleted order with id: " + orderId;
    }

    @PostMapping("/initialize/{customerId}")
    public WebshopOrder initializeWebshopOrder(@PathVariable Long customerId) {
        return orderService.initializeOrder(customerId);
    }

    @PostMapping("/{orderId}/add-item")
    public WebshopOrder addOrderItem(@Valid @RequestBody ProductRequestDto dto, @PathVariable long orderId) {
        return orderService.findOrderById(orderId)
                .map(order -> {
                    orderItemService.addOrderItem(order, dto.getProductId(), dto.getQuantity());
                    return order;
                })
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @DeleteMapping("/{orderId}/remove-item/{orderItemId}")
    public WebshopOrder removeOrderItem(@PathVariable long orderId, @PathVariable long orderItemId) {
        return orderService.findOrderById(orderId)
                .map(order -> {
                    orderItemService.removeOrderItem(orderItemId);
                    return order;
                })
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @PostMapping("/{orderId}/finalize")
    public WebshopOrder finalizeOrder(@PathVariable long orderId) {
        return orderService.finalizeOrder(orderId);
    }

}
