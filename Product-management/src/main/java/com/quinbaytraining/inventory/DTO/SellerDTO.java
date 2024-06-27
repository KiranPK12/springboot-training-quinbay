package com.quinbaytraining.inventory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    private String sellerName;
    private String sellerEmail;
    private String sellerPhoneNumber;
    private String sellerStreet;
    private String sellerCity;
    private String sellerState;
    private String sellerZipCode;
}
