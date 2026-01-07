package com.cps2.energy.web;

import com.cps2.energy.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void emptyDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldBeUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/users")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/users/" + UUID.randomUUID()))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(put("/api/users/" + UUID.randomUUID())
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void shouldGetUser() throws Exception {
        String location = createUser();
        String userJson = mockMvc.perform(get(location)
                        .with(jwt()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        UserRepresentation user = objectMapper.readValue(userJson, UserRepresentation.class);
        assertThat(user.id()).isNotNull();
    }

    @Test
    void shouldUpdateUser() throws Exception {
        String location = createUser();
        UserToUpdateRepresentation updateRequest = new UserToUpdateRepresentation(
                "john_updated", "john.updated@example.com", "John Updated", "USER");
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);
        String userJson = mockMvc.perform(put(location)
                        .content(updateRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        UserRepresentation user = objectMapper.readValue(userJson, UserRepresentation.class);
        assertThat(user.username()).isEqualTo("john_updated");
        assertThat(user.email()).isEqualTo("john.updated@example.com");
    }

    private String createUser() throws Exception {
        UserToCreateRepresentation userToCreate = userToCreate();
        String userToCreateJson = objectMapper.writeValueAsString(userToCreate);
        return mockMvc.perform(post("/api/users")
                        .content(userToCreateJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, notNullValue()))
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);
    }

    private UserToCreateRepresentation userToCreate() {
        return new UserToCreateRepresentation("johndoe", "password123",
                "john.doe@example.com", "John Doe", "USER");
    }

}
