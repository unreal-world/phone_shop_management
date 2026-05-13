package com.mycompany.techstore.model;

import java.util.List;

public class Product {
    private String productID;
    private String productName;
    private String brand;
    private Double price;
    private String description;
    private Integer stock_quantity;
    private List<Image> image;

    // Constructors
    public Product() {}

    public Product(String productID, String productName, String brand, Double price, String description, Integer stock_quantity) {
        this.productID = productID;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.stock_quantity = stock_quantity;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStock_quantity() {
        return stock_quantity;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStock_quantity(Integer stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }
    
}