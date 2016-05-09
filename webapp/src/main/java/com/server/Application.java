package com.server;
/**
 * Created by xiaoxu on 4/15/16.
 */

import com.server.model.ApiDataProvider;
import com.server.model.Data;
import com.server.model.SourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:7000", maxAge = 3600)
@SpringBootApplication
@RestController
public class Application implements EmbeddedServletContainerCustomizer {

    private StringBuilder sb = new StringBuilder();

    @Autowired
    private ApiDataProvider apiDataProvider;

    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    public String GetDataFromBrandServer(@RequestParam(value = "brand", defaultValue="惠普") String brand) {
        Data da = apiDataProvider.PickupItemViaBrand(SourceEnum.BRAND,"brand",brand);
        sb = BuildData(sb, da);
        return sb.toString();
    }

    @RequestMapping(value = "/model", method = RequestMethod.POST)
    public String GetDataFromModelServer(@RequestParam(value = "mod", defaultValue="AIR") String model) {
        Data da = apiDataProvider.PickupItemViaBrand(SourceEnum.MOD,"mod",model);
        sb = BuildData(sb, da);
        return sb.toString();
    }

    private StringBuilder BuildData(StringBuilder builder, Data data) {
        builder = new StringBuilder();
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
        container.setPort(7001);
    }

}
