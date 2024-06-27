package com.quinbaytraining.orders.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String customerId;
    private String customerName;
    List<OrderProductDTO> buyProductWithQuantities;

}
