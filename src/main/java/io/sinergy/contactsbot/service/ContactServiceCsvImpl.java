package io.sinergy.contactsbot.service;

import io.sinergy.contactsbot.model.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceCsvImpl implements ContactService {

    @Value("classpath:${contacts.filepath}")
    private Resource resource;

    private final List<Contact> contacts = new ArrayList<>();

    @PostConstruct
    private void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    contacts.add(new Contact(tokens[0], tokens[1], tokens[2], tokens[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> findAll() {
        return contacts;
    }
}
