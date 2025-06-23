package io.sinergy.contactsbot.runner;

import io.sinergy.contactsbot.service.ContactService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ContactRunner implements CommandLineRunner {

    private final ContactService contactService;

    public ContactRunner(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void run(String... args) {
        contactService.findAll().forEach(System.out::println);
    }
}
