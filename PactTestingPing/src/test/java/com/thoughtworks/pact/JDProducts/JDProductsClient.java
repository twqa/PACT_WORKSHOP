package com.thoughtworks.pact.JDProducts;

/**
 * Created by pingzhu on 5/17/16.
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component
public class JDProductsClient {

    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public JDProductsClient(@Value("${urls}") String url) {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    public JsonNode products() {
        ParameterizedTypeReference<JsonNode> responseType = new ParameterizedTypeReference<JsonNode>() {};

        return restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
    }
}
