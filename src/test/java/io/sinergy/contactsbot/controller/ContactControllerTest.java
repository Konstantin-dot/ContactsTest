package io.sinergy.contactsbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sinergy.contactsbot.model.Contact;
import io.sinergy.contactsbot.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ContactService contactService;

    private ContactController contactController;

    @BeforeEach
    void setUp() {
        contactController = new ContactController(contactService);
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllContacts() throws Exception {
        List<Contact> contacts = Arrays.asList(
                new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958"),
                new Contact("Petrova", "Olga", "Nikolaevna", "79154658923")
        );
        when(contactService.findAll()).thenReturn(contacts);

        mockMvc.perform(get("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(contacts)));
    }

    @Test
    void testGetContactById() throws Exception {
        String contactId = "9c8ce44c-f23f-4313-80f2-ebf0b8b52831";
        Contact contact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        when(contactService.findById(contactId)).thenReturn(Optional.of(contact));

        mockMvc.perform(get("/api/contacts/" + contactId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(contact)));
    }

    @Test
    void testGetContactByIdNotFound() throws Exception {
        String nonExistentId = "non-existent-id";
        when(contactService.findById(nonExistentId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/contacts/" + nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateContact() throws Exception {
        Contact contact = new Contact("Kukuschin", "Michael", "Sidorovich", "79054658231");
        when(contactService.save(any(Contact.class))).thenReturn(contact);

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(contact)));
    }

    @Test
    void testUpdateContact() throws Exception {
        String contactId = "9c8ce44c-f23f-4313-80f2-ebf0b8b52831";
        Contact updatedContact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        when(contactService.update(eq(contactId), any(Contact.class))).thenReturn(Optional.of(updatedContact));

        mockMvc.perform(put("/api/contacts/" + contactId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedContact))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedContact)));
    }

    @Test
    void testUpdateContactNotFound() throws Exception {
        String nonExistentId = "non-existent-id";
        Contact updatedContact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");
        when(contactService.update(eq(nonExistentId), any(Contact.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/contacts/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedContact))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteContact() throws Exception {
        String contactId = "9c8ce44c-f23f-4313-80f2-ebf0b8b52831";
        when(contactService.delete(contactId)).thenReturn(true);

        mockMvc.perform(delete("/api/contacts/" + contactId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteContactNotFound() throws Exception {
        String nonExistentId = "non-existent-id";
        when(contactService.delete(nonExistentId)).thenReturn(false);

        mockMvc.perform(delete("/api/contacts/" + nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}