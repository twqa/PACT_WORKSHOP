package com.server.model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xiaoxu on 4/19/16.
 */

abstract class DataProvider {

    @Autowired
    protected SourceProperty sourceProperty;


    public DataProvider() {
    }

    public JsonNode GetSource(SourceEnum sourceEnum, String para, String value) {
        return null;
    }


    public Data PickupItemViaBrand(SourceEnum sourceEnum, String para, String value) {
        Data data = new Data();
        String brand;
        String model;
        String provider;
        Double price;

        JsonNode root = GetSource(sourceEnum,para,value).path("Data");
        // 遍历 products 内的 array
        for (JsonNode objNode : root) {
            brand = objNode.get("brand").toString();
            model = objNode.get("model").toString();
            price = objNode.get("price").asDouble();
            provider = objNode.get("provider").toString();

            if (para == "mod"){
                if (model.contains(value) && PriceIsCheaperThanLastOne(price, data.getPrice())) {
                    data.setBrand(brand);
                    data.setModel(model);
                    data.setPrice(price);
                    data.setProvider(provider);
                }
            }else {
                if (brand.contains(value) && PriceIsCheaperThanLastOne(price, data.getPrice())) {
                    data.setBrand(brand);
                    data.setModel(model);
                    data.setPrice(price);
                    data.setProvider(provider);
                }
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
