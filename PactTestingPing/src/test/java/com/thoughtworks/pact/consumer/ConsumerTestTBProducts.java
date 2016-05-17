package com.thoughtworks.pact.consumer;

/**
 * Created by pingzhu on 5/17/16.
 */

import au.com.dius.pact.consumer.*;
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

public class ConsumerTestTBProducts {

    private static final String URL = "http://localhost:6003";
    private static String expectbody = "{\"products\": [{\"brand\": \"\u8054\u60f3\",\"model\": \"\u5c0f\u65b0Air\",\"price\": 6999}],\"provider\": \"\u4eac\u4e1c\"}";

    private JsonNode expectbodyjson() {

        JsonNode rootNode = null;

        ObjectMapper mapper = new ObjectMapper();
        //JSON ----> JsonNode
        try {
            rootNode = mapper.readTree(expectbody);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Iterator<String> keys = rootNode.fieldNames();
        System.out.println("hahahah");
        return rootNode;
    }

    @Rule
    public PactProviderRule provider = new PactProviderRule("tb_products_provider", "localhost", 6003, this);

    @Pact(provider="tb_products_provider", consumer="tb_products_consumer")
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
                .body(expectbody)
                .toFragment();
    }

    @Test
    @PactVerification("tb_products_provider")
    public void runTest() {
        TestCase.assertEquals(new TBProductsClient(URL + "/products").products(), expectbodyjson());
    }
}

