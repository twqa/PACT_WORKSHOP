package com.example.model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xiaoxu on 4/19/16.
 */

abstract class DataProvider {

    protected JsonNode provider;
    protected JsonNode products;

    @Autowired
    protected SourceProperty sourceProperty;


    public DataProvider() {
    }

    public JsonNode GetSource(SourceEnum sourceEnum) {
        return null;
    }

    public void MappingData(JsonNode root) {
        //mapping
        provider = root.path("provider");
        products = root.path("products");
    }

    public String ShowDemo(SourceEnum sourceEnum) {
        return GetSource(sourceEnum).toString();
    }


    public Data PickupItemViaModel(String in_model, SourceEnum sourceEnum) {
        Data data = new Data();
        String brand;
        String model;
        Double price;

        this.MappingData(GetSource(sourceEnum));

        // 遍历 products 内的 array
        for (JsonNode objNode : products) {
            brand = objNode.get("brand").toString();
            model = objNode.get("model").toString();
            price = objNode.get("price").asDouble();

            if (model.toUpperCase().contains(in_model.toUpperCase()) && PriceIsCheaperThanLastOne(price, data.getPrice())) {
                data.setBrand(brand);
                data.setModel(model);
                data.setPrice(price);
                data.setProvider(provider.toString());
            }
        }

        return data;
    }

    private Boolean PriceIsCheaperThanLastOne(Double price, Double current_price) {
        if (price - current_price < 0) {
            return true;
        }
        return false;
    }

}
