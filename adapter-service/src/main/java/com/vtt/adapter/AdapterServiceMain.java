package com.vtt.adapter;

import com.vtt.common.CommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({CommonConfiguration.class})
@SpringBootApplication
public class AdapterServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(AdapterServiceMain.class, args);
    }
}
