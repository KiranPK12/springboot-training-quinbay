package com.quinbaytraining.inventory.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductHistoryDTO {
    private String newValue;
    private String oldValue;
    private String modifiedColumn;
    private long prodId;
}
