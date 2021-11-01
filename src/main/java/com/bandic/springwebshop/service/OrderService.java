package com.bandic.springwebshop.service;

import com.bandic.springwebshop.exception.CustomerNotFoundException;
import com.bandic.springwebshop.exception.EntityNotFoundException;
import com.bandic.springwebshop.exception.OrderNotFoundException;
import com.bandic.springwebshop.model.Customer;
import com.bandic.springwebshop.model.OrderStatus;
import com.bandic.springwebshop.model.WebshopOrder;
import com.bandic.springwebshop.repository.CustomerRepository;
import com.bandic.springwebshop.repository.OrderItemRepository;
import com.bandic.springwebshop.repository.WebshopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final WebshopOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository itemRepository;
    private final EuroConverterService euroConverterService;

    public Iterable<WebshopOrder> finalAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<WebshopOrder> findOrderById(long orderId) {
        return orderRepository.findById(orderId);
    }

    public WebshopOrder initializeOrder(long customerId) {
        return customerRepository.findById(customerId)
                .map(this::createNewOrder)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    public void deleteOrderById(long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public WebshopOrder finalizeOrder(long orderId) {
      return orderRepository.findById(orderId)
                .map(this::calculateAndFinalizeOrder)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private WebshopOrder createNewOrder(Customer customer) {
        WebshopOrder webshopOrder = new WebshopOrder();
        webshopOrder.setCustomer(customer);
        webshopOrder.setStatus(OrderStatus.DRAFT);
        return webshopOrder;
    }

    private WebshopOrder calculateAndFinalizeOrder(WebshopOrder order) {
        List<BigDecimal> productPrizes = new ArrayList<>();
        itemRepository.findByWebshopOrderId(order.getId()).forEach(item -> productPrizes.add(item.totalItemPriceHrk()));
        BigDecimal totalPriceHrk = productPrizes.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPriceHrk(totalPriceHrk);

        BigDecimal euroExchangeRate = euroConverterService.getEuroExchangeRate();
        order.setTotalPriceEur(totalPriceHrk.multiply(euroExchangeRate));

        order.setStatus(OrderStatus.SUBMITTED);

        orderRepository.save(order);
        return order;
    }
}
