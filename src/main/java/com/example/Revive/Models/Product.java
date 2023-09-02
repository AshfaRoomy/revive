package com.example.Revive.Models;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String productName;
    private String brand;
    private String description;
    private String category;
    private double price;
    private int quantity;
    private String image;

    @Transient
    String[] catergoryArray;

    public Product(String productName,String brand, String description, String category, double price, int quantity, String image) {
        this.productName = productName;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }
    public Product(String productName,String brand, String description, double price, int quantity, String image, String[] catergoryArray) {
        this.productName = productName;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.catergoryArray = catergoryArray;
    }
}
