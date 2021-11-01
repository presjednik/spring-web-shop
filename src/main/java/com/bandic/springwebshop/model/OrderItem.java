package com.bandic.springwebshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private WebshopOrder webshopOrder;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private long quantity;

    public BigDecimal totalItemPriceHrk() {
        return product.getPriceHrk().multiply(BigDecimal.valueOf(quantity));
    }
}
