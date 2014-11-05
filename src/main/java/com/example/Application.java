package com.example;

import de.javakaffee.web.msm.MemcachedBackupSessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory()
            throws IOException {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addContextCustomizers(context -> {
            MemcachedBackupSessionManager m = new MemcachedBackupSessionManager();
            m.setMemcachedNodes("localhost:11211");
            m.setSticky(false);
            m.setStorageKeyPrefix("static:xx");
            context.setManager(m);
        });
        return factory;
    }
}