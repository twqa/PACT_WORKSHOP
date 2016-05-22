package com.thoughtworks.pact.consumerdsl;

/**
 * Created by pingzhu on 5/17/16.
 */

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pact.JDProducts.JDProductsClient;
import junit.framework.TestCase;
import net.sf.json.test.JSONAssert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConsumerTestBrandsJDProductsDsl {

    private static final String URL = "http://localhost:6003";
    private static String expectBody = "{\"products\": [{\"price\": 6999, \"model\": \"\u5c0f\u65b0Air\",\"brand\": \"联想\"}],\"provider\": \"\u4eac\u4e1c\"}";

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
    public PactProviderRule provider = new PactProviderRule("jd_products_provider", "localhost", 6003, this);

    @Pact(provider="jd_products_provider", consumer="dsl_brands_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Content-Type", "application/json");

        DslPart responseBody = new PactDslJsonBody()
                //.stringValue("provider", "京东1")
                .stringMatcher("provider", "京东\\d*", "京东")
                .minArrayLike("products", 1, 1)
                    .stringType("brand")
                    .stringType("model")
                    //.stringMatcher("model", "小新\\w*", "小新")
                    .numberType("price")
                    .closeObject()
                .closeArray();

        return builder
                .given("test_state")
                .uponReceiving("a_request_for_jd_products")
                    .path("/products")
                    .method("GET")
                .willRespondWith()
                    .headers(headers)
                    .status(200)
                    .body(responseBody)
                    //.body(expectBody)
                .toFragment();
    }

    @Test
    @PactVerification("jd_products_provider")
    public void runTest() {

        System.out.println("*********** start runTest ***********");

        JsonNode responseJson = null;

        responseJson = new JDProductsClient(URL + "/products").products();

        System.out.println("***********   ***********");

        System.out.println(responseJson);
        System.out.println(responseJson.get("products").getClass());
        System.out.println(responseJson.elements().next());

        System.out.println("*********** start assertion ***********");

        //JSONAssert.assertEquals(jsonNodeC, expectBodyJson(expectBody));
        TestCase.assertEquals(responseJson.get("provider"), expectBodyJson(expectBody).get("provider"));

        //new JDProductsClient(URL + "/products").products();
        System.out.println("*********** end runTest ***********");
    }
}
