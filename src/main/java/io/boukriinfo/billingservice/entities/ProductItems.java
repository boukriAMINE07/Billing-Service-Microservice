package io.boukriinfo.billingservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.boukriinfo.billingservice.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProductItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productID;
    private double quantity;
    private double price;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Bill bill;
    @Transient
    private Product product;
    @Transient
    private String productName;


}
