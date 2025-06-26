package io.sinergy.contactsbot.service;

import java.util.List;
import io.sinergy.contactsbot.model.Contact;

public interface ContactService {
    List<Contact> findAll();
}
