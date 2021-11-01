package com.bandic.springwebshop.model.dto;

import lombok.Data;

@Data
public class ProductRequestDto {
    private long productId;
    private int quantity;
}
