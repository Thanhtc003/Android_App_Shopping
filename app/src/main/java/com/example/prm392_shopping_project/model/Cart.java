package com.example.prm392_shopping_project.model;

public class Cart {


    int id;
    String name;
    double price;
    String unit;
    int quantity;
    byte[] img;

    public Cart(int id, String name, double price, String unit, int quantity,byte[] img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.img=img;
    }

    public Cart(String name, double price, String unit, int quantity,byte[] img) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.img=img;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
