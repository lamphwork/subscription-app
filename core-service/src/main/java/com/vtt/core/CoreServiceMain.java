package com.vtt.core;

import com.vtt.common.CommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import({CommonConfiguration.class})
@EnableScheduling
@SpringBootApplication
public class CoreServiceMain {

    public static void main(String[] args) {
         SpringApplication.run(CoreServiceMain.class, args);
    }
}
