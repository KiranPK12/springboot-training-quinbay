package com.quinbaytraining.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "seller_name", nullable = false)
    private String sellerName;
    @Column(name = "seller_email", nullable = false)
    private String sellerEmail;
    @Column(name = "seller_phone", nullable = false)
    private String sellerPhoneNumber;
    @Column(name = "seller_street", nullable = false)
    private String sellerStreet;
    @Column(name = "seller_city", nullable = false)
    private String sellerCity;
    @Column(name = "seller_state", nullable = false)
    private String sellerState;
    @Column(name = "seller_zipcode", nullable = false)
    private String sellerZipCode;


}
