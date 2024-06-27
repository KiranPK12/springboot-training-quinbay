package com.quinbaytraining.orders.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String prodName;
    private double prodPrice;
    private long prodQuantity;
    private long sellerId;
    private String categoryName;
}
