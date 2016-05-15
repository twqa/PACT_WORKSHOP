package com.thoughtworks.pact.consumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.thoughtworks.service.ConsumerClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


/**
 * Created by tlqiao on 5/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext.xml"
})
public class getDetailsByBrandTest {
	String url = "http://localhost:8000/local/brand=神舟";
	PactDslJsonBody body = new PactDslJsonBody().stringType("Data");

	@Autowired
	ConsumerClient consumerClient;
	String expect="{\"Data\":[{\"provider\": \"京东\",\"brand\": \"神舟\",\"model\": \"战神 K650D-I5 D2\",\"price\": 3109.0},{\"provider\": \"淘宝\",\"brand\": \"神舟\",\"model\": \" 战神K610D-i7 D2\",\"price\": 3499.0}]}";

	@Rule
	public PactProviderRule mockProvider = new PactProviderRule("local_getDetailsByBrand_provider", "localhost", 8000, this);


	@Pact(provider = "local_getDetailsByBrand_provider", consumer = "getDetailsByBrand_consumer")
	public PactFragment createFragment(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "text/plain;charset=UTF-8");
		return builder
				.given("test state")
				.uponReceiving("consumer test by pact for getDetailsByBrand")
				.path("/local/brand=神舟")
				.method("GET")
				.willRespondWith()
				.status(200)
				.headers(headers)
				.body(expect)
				.toFragment();
	}

	@Test
	@PactVerification("local_getDetailsByBrand_provider")
	public void runTest() {
		String response = consumerClient.getDetailsByBrand(url);
		assertEquals(expect,expect);
	}
}

