package com.thoughtworks.service;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;



/**
 * Created by tlqiao on 5/11/16.
 */


public class ConsumerClient {
@Autowired
RestTemplate restTemplate;

	@Autowired
	private DefaultHttpClient httpClient;
	@Autowired
	private  HttpGet httpGet;


	public String getDetailsByBrand(String url) {


		String response= restTemplate.exchange(url, HttpMethod.GET,null,String.class).getBody();
		return response;
	}

	//write two way for Junit test, you can ignore below function

	public String getDetailsByBrand2(String url) {
		try {
			httpGet = createHttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			return EntityUtils.toString(httpResponse.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	private HttpGet createHttpGet(String url) {

		httpGet=new HttpGet(url);
		httpGet.setHeader("Content-Type", "text/plain;charset=UTF-8");
		return httpGet;
	}
}
