package com.quinbaytraining.orders.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {
    private long id;
    private long buyQuantity;
}
