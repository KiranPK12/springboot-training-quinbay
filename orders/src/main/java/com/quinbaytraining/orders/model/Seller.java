package com.quinbaytraining.orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    private Long id;
    private String sellerName;
    private String sellerEmail;
    private String sellerPhoneNumber;
    private String sellerStreet;
    private String sellerCity;
    private String sellerState;
    private String sellerZipCode;
}
