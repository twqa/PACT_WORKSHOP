package com.thoughtworks.pact.models;

/**
 * Created by pingzhu on 5/13/16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ModelsClient {

    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public ModelsClient(@Value("${urls}") String url) {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    public String models() {
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};

        return restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
    }
}