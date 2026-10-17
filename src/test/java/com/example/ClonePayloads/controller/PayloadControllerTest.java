package com.example.ClonePayloads.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PayloadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar 400 quando quantidade < 2")
    void testQuantidadeInvalida() throws Exception {
        String json = """
                    {
                      "quantidade": 1,
                      "payload": {
                        "timestamp": "2025-12-04T15:40:12.487Z",
                        "name": "rodolfo"
                      }
                    }
                """;

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.quantidade")
                        .value("A quantidade tem que ser maior ou igual a 2"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando payload é nulo")
    void testPayloadNull() throws Exception {
        String json = """
                    {
                      "quantidade": 2,
                      "payload": null
                    }
                """;

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.payload")
                        .value("O payload não pode ser nulo"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando payload é vazio")
    void testPayloadEmpty() throws Exception {
        String json = """
                    {
                      "quantidade": 2,
                      "payload": {}
                    }
                """;

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.payload")
                        .value("O payload não pode estar vazio"));
    }

    @Test
    @DisplayName("Deve retornar 200 para payload válido")
    void testPayloadValido() throws Exception {
        String json = """
                    {
                      "quantidade": 2,
                      "payload": {
                        "timestamp": "2025-12-04T15:40:12.487Z",
                        "name": "rodolfo"
                      }
                    }
                """;

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(strings={
        "{\"quantidade\":101,\"payload\":{\"timestamp\":\"2026-01-01T00:00:00Z\"}}",
        "{\"quantidade\":2,\"payload\":{\"x\":1},\"fieldRoles\":[null]}",
        "{\"quantidade\":2,\"payload\":{\"x\":1},\"fieldRoles\":[{\"field\":\"x\",\"strategy\":\"INVALID\"}]}",
        "{\"quantidade\":2,\"payload\":{\"x\":1},\"fieldRoles\":[{\"field\":\"x\",\"strategy\":\"UUID\"}]}",
        "{\"quantidade\":2,\"payload\":{\"x\":1},\"fieldRoles\":[{\"field\":\"absent\",\"strategy\":\"UUID\"}]}",
        "not-json"
    })
    void rejectsInvalidRequests(String json) throws Exception {
        mockMvc.perform(post("/api/generate").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
    }
    @Test void enforcesBodyLimit() throws Exception {
        mockMvc.perform(post("/api/generate").servletPath("/api/generate").contentType(MediaType.APPLICATION_JSON).content("x".repeat(65537))).andExpect(status().isPayloadTooLarge());
    }
    @Test void enforcesFieldLimit() throws Exception {
        String fields=java.util.stream.IntStream.range(0,101).mapToObj(i->"\"f"+i+"\":0").collect(java.util.stream.Collectors.joining(","));
        mockMvc.perform(post("/api/generate").contentType(MediaType.APPLICATION_JSON).content("{\"quantidade\":2,\"payload\":{"+fields+"},\"fieldRoles\":[]}")).andExpect(status().isBadRequest());
    }
}

