package com.thoughtworks.pact.consumer;

/**
 * Created by pingzhu on 5/13/16.
 */

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.thoughtworks.pact.brands.ConsumerClient;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsumerTest {

    @Rule
    public PactProviderRule provider = new PactProviderRule("test_provider", "localhost", 8000, this);

    @Pact(provider="test_provider", consumer="test_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        //headers.put("Content-Type", "application/json;charset=UTF-8");
        //headers.put("Content-Type", "text/plain;charset=UTF-8");

        return builder
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"provider\": \"JD\", \"brand\": \"SZ\"}")
                .given("test state")
                .uponReceiving("ExampleJavaConsumerPactRuleTest second test interaction")
                .method("OPTIONS")
                .headers(headers)
                .path("/second")
                .body("")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("")
                .toFragment();
    }

    @Test
    @PactVerification("test_provider")
    public void runTest() throws IOException {
        Assert.assertEquals(new ConsumerClient("http://localhost:8000").options("/second"), 200);
        Map expectedResponse = new HashMap();
        expectedResponse.put("provider", "JD");
        expectedResponse.put("brand", "SZ");
        assertEquals(new ConsumerClient("http://localhost:8000").getAsMap("/", ""), expectedResponse);
    }
}
