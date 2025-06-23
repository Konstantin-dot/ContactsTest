package io.sinergy.contactsbot.service;

import io.sinergy.contactsbot.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceCsvImplTest {

    @InjectMocks
    private ContactServiceCsvImpl contactService;

    @Mock
    private Resource resource;

    private List<Contact> contacts;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Use reflection to access and modify the private 'contacts' field
        Field contactsField = ContactServiceCsvImpl.class.getDeclaredField("contacts");
        contactsField.setAccessible(true);
        contacts = new ArrayList<>();
        contactsField.set(contactService, contacts);
    }

    @Test
    void findAll_ReturnsAllContacts() {
        // Arrange
        Contact contact1 = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        Contact contact2 = new Contact("Petrova", "Olga", "Nikolaevna", "79154658923");
        contacts.addAll(Arrays.asList(contact1, contact2));

        // Act
        List<Contact> result = contactService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(contact1, contact2)));
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoContacts() {
        // Act
        List<Contact> result = contactService.findAll();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ReturnsContact_WhenContactExists() {
        // Arrange
        Contact contact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        contacts.add(contact);

        // Act
        Optional<Contact> result = contactService.findById(contact.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(contact, result.get());
    }

    @Test
    void findById_ReturnsEmpty_WhenContactDoesNotExist() {
        // Act
        Optional<Contact> result = contactService.findById("non-existent-id");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void save_AddsContactWithNewId() {
        // Arrange
        Contact contact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        String originalId = contact.getId();

        // Act
        Contact savedContact = contactService.save(contact);

        // Assert
        assertNotNull(savedContact.getId());
        assertNotEquals(originalId, savedContact.getId()); // New UUID is generated
        assertEquals(contact.getLastName(), savedContact.getLastName());
        assertEquals(contact.getFirstName(), savedContact.getFirstName());
        assertEquals(contact.getMiddleName(), savedContact.getMiddleName());
        assertEquals(contact.getPhone(), savedContact.getPhone());
        assertEquals(1, contacts.size());
        assertTrue(contacts.contains(savedContact));
    }

    @Test
    void update_UpdatesContact_WhenContactExists() {
        // Arrange
        Contact existingContact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        contacts.add(existingContact);
        Contact updatedContact = new Contact("Petrov", "Petr", "Petrovich", "79154658923");

        // Act
        Optional<Contact> result = contactService.update(existingContact.getId(), updatedContact);

        // Assert
        assertTrue(result.isPresent());
        Contact updated = result.get();
        assertEquals(existingContact.getId(), updated.getId());
        assertEquals(updatedContact.getLastName(), updated.getLastName());
        assertEquals(updatedContact.getFirstName(), updated.getFirstName());
        assertEquals(updatedContact.getMiddleName(), updated.getMiddleName());
        assertEquals(updatedContact.getPhone(), updated.getPhone());
        assertEquals(1, contacts.size());
    }

    @Test
    void update_ReturnsEmpty_WhenContactDoesNotExist() {
        // Arrange
        Contact updatedContact = new Contact("Petrov", "Petr", "Petrovich", "79154658923");

        // Act
        Optional<Contact> result = contactService.update("non-existent-id", updatedContact);

        // Assert
        assertFalse(result.isPresent());
        assertTrue(contacts.isEmpty());
    }

    @Test
    void delete_RemovesContact_WhenContactExists() {
        // Arrange
        Contact contact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        contacts.add(contact);

        // Act
        boolean result = contactService.delete(contact.getId());

        // Assert
        assertTrue(result);
        assertTrue(contacts.isEmpty());
    }

    @Test
    void delete_ReturnsFalse_WhenContactDoesNotExist() {
        // Act
        boolean result = contactService.delete("non-existent-id");

        // Assert
        assertFalse(result);
        assertTrue(contacts.isEmpty());
    }
}