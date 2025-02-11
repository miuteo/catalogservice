package com.miu.teo.book.cloudnativespring.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
//@EnableConfigurationProperties(PolarProperties.class)
public class CatalogserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogserviceApplication.class, args);
    }

}
