package io.sinergy.contactsbot.service;

import io.sinergy.contactsbot.model.Contact;
import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> findAll();
    Optional<Contact> findById(String id);
    Contact save(Contact contact);
    Optional<Contact> update(String id, Contact contact);
    boolean delete(String id);
}
