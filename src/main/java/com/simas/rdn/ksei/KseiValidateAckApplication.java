package com.simas.rdn.ksei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class KseiValidateAckApplication {

    public static void main(String[] args) {
        SpringApplication.run(KseiValidateAckApplication.class, args);
    }

}
