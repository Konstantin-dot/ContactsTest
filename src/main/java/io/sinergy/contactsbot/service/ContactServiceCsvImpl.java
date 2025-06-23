package io.sinergy.contactsbot.service;

import io.sinergy.contactsbot.model.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ContactServiceCsvImpl implements ContactService {

    @Value("classpath:${contacts.filepath}")
    private Resource resource;

    private final List<Contact> contacts = new ArrayList<>();

    @PostConstruct
    private void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    Contact contact = new Contact(tokens[0], tokens[1], tokens[2], tokens[3]);
                    contacts.add(contact);
                }
            }
            System.out.println("Loaded contacts: " + contacts.size());
            contacts.forEach(c -> System.out.println(c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Cacheable("contacts")
    public List<Contact> findAll() {
        System.out.println("Loading from CSV (not from cache)");
        return contacts;
    }

    @Override
    @Cacheable(value = "contactById", key = "#id")
    public Optional<Contact> findById(String id) {
        System.out.println("Finding by ID (not from cache): " + id);
        return contacts.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    @CacheEvict(value = {"contacts", "contactById"}, allEntries = true)
    public Contact save(Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        contacts.add(contact);
        return contact;
    }

    @Override
    @CacheEvict(value = {"contacts", "contactById"}, allEntries = true)
    public Optional<Contact> update(String id, Contact updated) {
        return findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setMiddleName(updated.getMiddleName());
            existing.setPhone(updated.getPhone());
            return existing;
        });
    }

    @Override
    @CacheEvict(value = {"contacts", "contactById"}, allEntries = true)
    public boolean delete(String id) {
        return contacts.removeIf(c -> c.getId().equals(id));
    }
}
