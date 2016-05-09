package com.example.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by xiaoxu on 4/19/16.
 */

@Component
public class LocalDataProvider extends DataProvider {

    public LocalDataProvider() {
    }

    @Override
    public JsonNode GetSource(SourceEnum sourceEnum) {
        try {
            sourceProperty.Init(sourceEnum);
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readTree(new File(sourceProperty.getLocal()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {

        }
        return null;
    }

}
