package com.example.prm392_shopping_project.model;

public class RecentlyViewed {

    int id;
    String name;
    String description;
    int price;
    String quantity;
    String unit;
    private byte[] imageUrl;
    private byte[] bigImageUrl;

    public RecentlyViewed(int id, String name, String description, int price, String quantity, String unit, byte[] imageUrl, byte[] bigimageurl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.imageUrl = imageUrl;
        this.bigImageUrl = bigimageurl;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
}
