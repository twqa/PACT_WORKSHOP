package com.thoughtworks.pact.consumer;

/**
 * Created by pingzhu on 5/17/16.
 */

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pact.JDProducts.JDProductsClient;
import com.thoughtworks.pact.TBProducts.TBProductsClient;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConsumerTestModelsTBProducts {

    private static final String URL = "http://localhost:6003";
    private static String expectBody = "{\"products\": [{\"brand\": \"\u8054\u60f3\",\"model\": \"\u5c0f\u65b0Air\",\"price\": 6999}],\"provider\": \"\u4eac\u4e1c\"}";

    private JsonNode expectBodyJson(String jsonStr) {
        JsonNode jsonNode = null;

        ObjectMapper omapper = new ObjectMapper();

        try {
            jsonNode = omapper.readTree(jsonStr);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }

    @Rule
    public PactProviderRule provider = new PactProviderRule("tb_products_provider", "localhost", 6003, this);

    @Pact(provider="tb_products_provider", consumer="models_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Content-Type", "application/json");

        return builder
                .given("test_state")
                .uponReceiving("a_request_for_jd_products")
                .path("/products")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(expectBody)
                .toFragment();
    }

    @Test
    @PactVerification("tb_products_provider")
    public void runTest() {

        JsonNode responseJson = null;

        responseJson = new TBProductsClient(URL + "/products").products();

        //the assertion for expected provider response and mocked provider response
        TestCase.assertEquals(responseJson, expectBodyJson(expectBody));

    }
}

