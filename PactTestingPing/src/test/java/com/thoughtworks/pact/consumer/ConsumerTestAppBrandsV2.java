package com.thoughtworks.pact.consumer;

/**
 * Created by pingzhu on 5/13/16.
 */

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.thoughtworks.pact.brands.BrandsClient;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ConsumerTestAppBrandsV2 {

    private static final String URL = "http://localhost:8000";
    private static String expectbody = "{\"Data\":[{\"provider\": \"京东\",\"brand\": \"神舟\",\"model\": \" 战神K610D-i7 D2\",\"price\": 3469.0},{\"provider\": \"淘宝\",\"brand\": \"神舟\",\"model\": \"战神 K650D-I5 D2\",\"price\": 3076.0}]}";
    private static String expectbody2 = "{\"Data\":[{\"provider\": \"京东\",\"brand\": \"华硕\",\"model\": \"R557LI\",\"price\": 3599.0},{\"provider\": \"淘宝\",\"brand\": \"华硕\",\"model\": \"X552WE5100\",\"price\": 2387.0}]}";

    @Rule
    public PactProviderRule provider = new PactProviderRule("brands_provider_v2", "localhost", 8000, this);

    @Pact(provider="brands_provider_v2", consumer="app_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Content-Type", "text/plain;charset=UTF-8");

        return builder
                .given("test_state")
                .uponReceiving("a_request_for_Brands SZ")
                    .path("/api/brand=神舟")
                    .method("GET")
                .willRespondWith()
                    .headers(headers)
                    .status(200)
                    .body(expectbody)

                .given("test_state")
                .uponReceiving("a_request_for_Brands HS")
                    .path("/api/brand=华硕")
                    .method("GET")
                .willRespondWith()
                    .headers(headers)
                    .status(200)
                    .body(expectbody2)

                .toFragment();
    }

    @Test
    @PactVerification("brands_provider_v2")
    public void runTest() {

        String responseStr = "";
        String responseStr2 = "";

        //Pactice 2: no actual consumer request call, a simulated consumer class used
        responseStr = new BrandsClient(URL + "/api/brand=神舟").brands();
        responseStr2 = new BrandsClient(URL + "/api/brand=华硕").brands();

        //the assertion for expected provider response and mocked provider response
        TestCase.assertEquals(responseStr, expectbody);
        TestCase.assertEquals(responseStr2, expectbody2);

    }
}
