package io.sinergy.contactsbot.controller;

import io.sinergy.contactsbot.model.Contact;
import io.sinergy.contactsbot.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final ContactService contactService;

    public UserController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> findById(@PathVariable String id) {
        return contactService.findById(id)
                .map(contact -> ResponseEntity.ok(contact))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}