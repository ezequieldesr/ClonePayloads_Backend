package com.example.ClonePayloads.ClonePayloads.service;

import com.example.ClonePayloads.ClonePayloads.dto.PayloadRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;



@DisplayName("PayloadServiceTest")
public class PayloadServiceTest {

    private PayloadService payloadService;
    private Validator validator;

    @BeforeEach
    void setUp() {
        payloadService = new PayloadService();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Teste payload com timestamp válido")
    void testTimestampValid() {
        PayloadRequest pr = new PayloadRequest();
        pr.setQuantidade(2);
        pr.setPayload(Map.of(
                "timestamp", "2025-12-04T15:40:12.487Z",
                "name", "rodolfo"
        ));

        Set<ConstraintViolation<PayloadRequest>> violations = validator.validate(pr);
        assertTrue(violations.isEmpty(), "Não deveria haver violações");
    }

    @Test
    @DisplayName("Teste payload sem timestamp")
    void testTimestampMissing() {
        PayloadRequest pr = new PayloadRequest();
        pr.setQuantidade(2);
        pr.setPayload(Map.of("name", "rodolfo"));

        Set<ConstraintViolation<PayloadRequest>> violations = validator.validate(pr);
        assertTrue(violations.isEmpty(), "Sem validação específica de timestamp, logo deve ser válido");
    }
}
