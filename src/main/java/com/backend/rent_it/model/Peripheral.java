package com.backend.rent_it.model;

public class Peripheral {
    private int id;
    private String name;
    private int quantity;
    private byte[] image;  // Image as byte array
    private String type;
    private double price;
    private String description;

    // Default constructor
    public Peripheral() {}

    // Parameterized constructor
    public Peripheral(int id, String name, int quantity, byte[] image, String type, double price, String description) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.image = image;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    // Getters and setters
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Peripheral{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", image=" + (image != null ? image.length + " bytes" : "null") +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
