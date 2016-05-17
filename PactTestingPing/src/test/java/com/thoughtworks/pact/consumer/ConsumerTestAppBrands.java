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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ConsumerTestAppBrands {

    private static final String URL = "http://localhost:8000";
    private static String expectbody = "{\"Data\":[{\"provider\": \"京东\",\"brand\": \"神舟\",\"model\": \" 战神K610D-i7 D2\",\"price\": 3469.0},{\"provider\": \"淘宝\",\"brand\": \"神舟\",\"model\": \"战神 K650D-I5 D2\",\"price\": 3076.0}]}";

    @Rule
    public PactProviderRule provider = new PactProviderRule("brands_provider", "localhost", 8000, this);

    @Pact(provider="brands_provider", consumer="brands_consumer")
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
        //TestCase.assertEquals(new BrandsClient(URL + "/api/brand=神舟").brands(), Arrays.asList(new Brands(), new Brands(), new Brands(), new Brands()));
        TestCase.assertEquals(new BrandsClient(URL + "/api/brand=神舟").brands(), expectbody);

        /*Map expectedResponse = new HashMap();
        expectedResponse.put("price", 42);
        Assert.assertEquals(new BrandsClient(URL + "/api/brand=神舟").brands(), expectedResponse);
        */
    }
}
