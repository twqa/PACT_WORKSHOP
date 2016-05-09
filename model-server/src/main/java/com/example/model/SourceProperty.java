package com.example.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoxu on 4/26/16.
 */
@Component
public class SourceProperty {
    //读配置文件,默认是resources/application.yml
    @Autowired
    private Environment environment;

    private String api;
    private String local;

    public String getApi() {
        return api;
    }


    public String getLocal() {
        return local;
    }


    public void setApi(String api) {
        this.api = api;
    }


    public void setLocal(String local) {
        this.local = local;
    }


    public void Init(SourceEnum sourceEnum) {

        if (sourceEnum == SourceEnum.JD){
            setApi(environment.getProperty("path.JDapi"));
            setLocal(environment.getProperty("path.JDlocal"));
        }
        else {
            setApi(environment.getProperty("path.TBapi"));
            setLocal(environment.getProperty("path.TBlocal"));
        }
    }

    SourceProperty() {

    }

}
