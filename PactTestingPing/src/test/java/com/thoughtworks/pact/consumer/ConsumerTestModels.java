package com.thoughtworks.pact.consumer;

/**
 * Created by pingzhu on 5/13/16.
 */

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pact.brands.BrandsClient;
import com.thoughtworks.pact.models.ModelsClient;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConsumerTestModels {

    private static final String URL = "http://localhost:8010";
    private static String expectbody = "{\"Data\":[{\"provider\": \"京东\",\"brand\": \"神舟\",\"model\": \" 战神K610D-i7 D2\",\"price\": 3469.0},{\"provider\": \"淘宝\",\"brand\": \"神舟\",\"model\": \"战神 K650D-I5 D2\",\"price\": 3076.0}]}";

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
    public PactProviderRule provider = new PactProviderRule("models_provider", "localhost", 8010, this);

    @Pact(provider="models_provider", consumer="models_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Content-Type", "text/plain;charset=UTF-8");

        return builder
                .given("test_state")
                .uponReceiving("a_request_for_Models")
                .path("/api/mod=战神")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(expectbody)
                .toFragment();
    }

    @Test
    @PactVerification("models_provider")
    public void runTest() {
        TestCase.assertEquals(new ModelsClient(URL + "/api/mod=战神").models(), expectbody);

    }
}
