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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ContactService contactService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(contactService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetContactById() throws Exception {
        String contactId = "24fa2a06-0496-42c8-ba59-e0ffe5a97621";
        Contact contact = new Contact("Ivanov", "Ivan", "Ivanovich", "79035687958");

        when(contactService.findById(contactId)).thenReturn(Optional.of(contact));

        mockMvc.perform(get("/contacts/" + contactId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(contact)));
    }

    @Test
    void testGetContactByIdNotFound() throws Exception {
        String nonExistentId = "non-existent-id";

        when(contactService.findById(nonExistentId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/contacts/" + nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}