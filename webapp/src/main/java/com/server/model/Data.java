package com.server.model;

/**
 * Created by xiaoxu on 4/19/16.
 */
public class Data {

    private String provider;
    private String brand;
    private String model;
    private Double price;

    public Data() {
        price = 100000.0;
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

    public Double getPrice() {
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

    public void setPrice(Double price) {
        this.price = price;
    }


}
