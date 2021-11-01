package com.bandic.springwebshop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Size(min = 10, max = 10, message = "Code can have exactly 10 characters, and needs to be unique")
    private String code;

    private String name;

    @Min(0)
    private BigDecimal priceHrk;

    private String description;

    private Boolean isAvailable;
}
