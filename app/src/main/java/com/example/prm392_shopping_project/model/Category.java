package com.example.prm392_shopping_project.model;

import java.util.List;

public class Category {

    private int id;
    private String name;
    private byte[] imageUrl;

    private List<Product> products;
    private Category(){

    }
    public Category(String name, byte[] imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
    public Category(int id, String name, byte[] imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
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

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {}
}
