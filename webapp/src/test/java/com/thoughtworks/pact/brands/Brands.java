package com.thoughtworks.pact.brands;

/**
 * Created by pingzhu on 5/13/16.
 */

public class Brands {

    private String provider;
    private String brand;
    private String model;
    private int price;

    public Brands() {
    }

    public Brands(int price) {
        this.price = price;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    public int getPrice() {
        return this.price;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    private StringBuilder BuildData(StringBuilder builder) {
        builder = new StringBuilder();
        builder.append("{");
        builder.append("\"provider\": ");
        builder.append(provider);
        builder.append(",\"brand\": ");
        builder.append(brand);
        builder.append(",\"model\": ");
        builder.append(model);
        builder.append(",\"price\": ");
        builder.append(price);
        builder.append("}");

        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brands brands = (Brands) o;

        if (price != brands.price) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return price;
    }
}
