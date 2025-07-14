package com.example.prm392_shopping_project.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String unit;
    private int discount;
    private byte[] imageUrl;
    private byte[] bigImageUrl;

    private int categoryId;
    private Category category;

    public Product() {
    }
    public Product(String name, String description, double price, int quantity, String unit,int categoryId, int discount,
                   byte[] imageUrl, byte[] bigImageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.imageUrl = imageUrl;
        this.bigImageUrl = bigImageUrl;
        this.categoryId = categoryId;
        this.unit = unit;
    }

    public Product(int id,String name, String description, double price, int quantity,String unit, int categoryId, int discount,
                   byte[] imageUrl, byte[] bigImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.imageUrl = imageUrl;
        this.bigImageUrl = bigImageUrl;
        this.categoryId = categoryId;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(byte[] bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
