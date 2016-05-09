package com.example;
/**
 * Created by xiaoxu on 4/15/16.
 */

import com.example.model.ApiDataProvider;
import com.example.model.Data;
import com.example.model.LocalDataProvider;
import com.example.model.SourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application implements EmbeddedServletContainerCustomizer {

    @Autowired
    private LocalDataProvider localDataProvider;

    @Autowired
    private ApiDataProvider apiDataProvider;

    @RequestMapping(value = "/api/demo")
    public String Showdemo() {
        return apiDataProvider.ShowDemo(SourceEnum.JD);
    }

    @RequestMapping("/local/mod={mod}")
    public String ShowData(@PathVariable("mod") String mod) {
        Data jd_da = localDataProvider.PickupItemViaModel(mod, SourceEnum.JD);
        Data tb_da = localDataProvider.PickupItemViaModel(mod, SourceEnum.TB);

        StringBuilder sb = new StringBuilder();
        sb.append("{\"Data\":[");
        sb = BuildData(sb, jd_da);
        sb.append(",");
        sb = BuildData(sb, tb_da);
        sb.append("]}");
        return sb.toString();
    }

    @RequestMapping("/api/mod={mod}")
    public String ShowAPIData(@PathVariable("mod") String mod) {
        Data jd_da = apiDataProvider.PickupItemViaModel(mod, SourceEnum.JD);
        Data tb_da = apiDataProvider.PickupItemViaModel(mod, SourceEnum.TB);

        StringBuilder sb = new StringBuilder();
        sb.append("{\"Data\":[");
        sb = BuildData(sb, jd_da);
        sb.append(",");
        sb = BuildData(sb, tb_da);
        sb.append("]}");
        return sb.toString();
    }


    private StringBuilder BuildData(StringBuilder builder, Data data) {
//        builder = new StringBuilder();
        builder.append("{");
        builder.append("\"provider\": ");
        builder.append(data.getProvider());
        builder.append(",\"brand\": ");
        builder.append(data.getBrand());
        builder.append(",\"model\": ");
        builder.append(data.getModel());
        builder.append(",\"price\": ");
        builder.append(data.getPrice().toString());
        builder.append("}");

        return builder;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8010);
    }

}
