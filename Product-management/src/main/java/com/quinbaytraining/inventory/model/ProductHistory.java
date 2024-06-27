package com.quinbaytraining.inventory.model;

import com.quinbaytraining.inventory.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products_history")
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    @Column(name = "new_value", nullable = false)
    private String newValue;
    @Column(name = "old_value", nullable = false)
    private String oldValue;
    @Column(name = "modified_column", nullable = false)
    private String modifiedColumn;
    @Column(name = "date_modified", nullable = false)
    private String dateModified;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}