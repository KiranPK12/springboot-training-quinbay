package com.quinbaytraining.inventory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String prodName;
    private double prodPrice;
    private int prodQuantity;
    private long sellerId;
    private String categoryName;
}
