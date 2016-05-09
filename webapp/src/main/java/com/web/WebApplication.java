package com.web;
/**
 * Created by xiaoxu on 4/15/16.
 */

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;


@SpringBootApplication
@Controller
public class WebApplication implements EmbeddedServletContainerCustomizer {

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/")
    public String hello() {
        return "hello";

    }

//    @RequestMapping("/}")
//    public String GetDataString(@PathVariable("brand") String brand, Model model) {
//        JsonNode root = restTemplate.postForObject("http://localhost:8081/local?brand=" + brand, null, JsonNode.class);
//        model.addAttribute("brand", root);
//        return "hello";
//    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }


    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(7000);
    }

}
