package com.example.model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xiaoxu on 4/19/16.
 */

@Component
public class ApiDataProvider extends DataProvider {

    private RestTemplate restTemplate = new RestTemplate();

    public ApiDataProvider() {
    }

    @Override
    public JsonNode GetSource(SourceEnum sourceEnum) {
        sourceProperty.Init(sourceEnum);
        return restTemplate.getForObject(sourceProperty.getApi(), JsonNode.class);
    }

}
