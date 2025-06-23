package io.sinergy.contactsbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ContactsBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactsBotApplication.class, args);
    }
}
