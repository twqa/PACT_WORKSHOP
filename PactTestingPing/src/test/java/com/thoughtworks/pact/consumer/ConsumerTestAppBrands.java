package com.thoughtworks.pact.consumer;

/**
 * Created by pingzhu on 5/13/16.
 */

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.model.PactFragment;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pact.brands.BrandsClient;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ConsumerTestAppBrands {

    private static final String URL = "http://localhost:8000";
    private static String expectbody = "{\"Data\":[{\"provider\": \"京东\",\"brand\": \"神舟\",\"model\": \" 战神K610D-i7 D2\",\"price\": 3469.0},{\"provider\": \"淘宝\",\"brand\": \"神舟\",\"model\": \"战神 K650D-I5 D2\",\"price\": 3076.0}]}";

    @Rule
    public PactProviderRule provider = new PactProviderRule("brands_provider", "localhost", 8000, this);

    @Pact(provider="brands_provider", consumer="app_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Content-Type", "text/plain;charset=UTF-8");

        return builder
                .given("test_state")
                .uponReceiving("a_request_for_Brands")
                    .path("/api/brand=神舟")
                    .method("GET")
                .willRespondWith()
                    .headers(headers)
                    .status(200)
                    .body(expectbody)
                .toFragment();
    }

    @Test
    @PactVerification("brands_provider")
    public void runTest() {

        String responseStr = "";

        //Pactice 1: no actual consumer request call, response the mock provider body directly
        RestTemplate restTemplate;
        restTemplate = new RestTemplate();

        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};

        responseStr = restTemplate.exchange(URL + "/api/brand=神舟", HttpMethod.GET, null, responseType).getBody();

        //Pactice 2: no actual consumer request call, a simulated consumer class used
//        responseStr = new BrandsClient(URL + "/api/brand=神舟").brands();

        //the assertion for expected provider response and mocked provider response
        TestCase.assertEquals(responseStr, expectbody);

    }
}
