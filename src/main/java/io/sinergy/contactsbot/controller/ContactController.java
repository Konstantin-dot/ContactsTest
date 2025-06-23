package io.sinergy.contactsbot.controller;

import io.sinergy.contactsbot.model.Contact;
import io.sinergy.contactsbot.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
        return contactService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactService.save(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact contact) {
        return contactService.update(id, contact)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        if (contactService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
