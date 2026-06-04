package com.example.ClonePayloads.service;

import com.example.ClonePayloads.dto.FieldRole;
import com.example.ClonePayloads.dto.PayloadRequest;
import com.example.ClonePayloads.dto.PayloadResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PayloadService {
    public PayloadResponse duplicate(PayloadRequest payloadRequest){
        List<Map<String, Object>> result = new ArrayList<>();

        for(int i = 0; i < payloadRequest.getQuantidade(); i++){
            result.add(generatorPayload(payloadRequest.getPayload(), payloadRequest.getFieldRoles(),i + 1));
        }
        return new PayloadResponse(result);
    }

    public Map<String, Object> generatorPayload(Map<String, Object> original, List<FieldRole> fieldRoles, int index) {
        Map<String, Object> clone = new HashMap<>(original);

        if (fieldRoles == null || fieldRoles.isEmpty()) {
            applyTimestampIncrement(clone, "timestamp", index);
            return clone;
        }

        for (FieldRole rule : fieldRoles) {
            String field = rule.getField();
            String strategy = rule.getStrategy();

            switch (strategy) {
                case "TIMESTAMP_INCREMENT" -> applyTimestampIncrement(clone, field, index);
                case "NUMERIC_INCREMENT" -> applyNumericIncrement(clone, field, index);
                case "UUID"-> clone.put(field, UUID.randomUUID().toString());
                case "FIXED_VALUES" -> applyFixedValues(clone, field, rule.getValues(), index);
                default -> throw new IllegalArgumentException("Estratégia desconhecida: " + strategy);
            }
        }
        return clone;
    }

    private void applyTimestampIncrement(Map<String, Object> clone, String field, int addMillis) {
        Object ts = clone.get(field);
        if (ts == null || !(ts instanceof String) || ((String) ts).isBlank()) {
            throw new IllegalArgumentException("O campo '" + field + "' precisa ser uma String ISO-8601 presente no payload");
        }
        try {
            Instant base = Instant.parse((String) ts);
            clone.put(field, base.plusMillis(addMillis).toString());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("O campo '" + field + "' deve estar no formato ISO-8601: " + ts);
        }
    }

    private void applyNumericIncrement(Map<String, Object> clone, String field, int index) {
        Object val = clone.get(field);
        if (!(val instanceof Number)) {
            throw new IllegalArgumentException("O campo '" + field + "' precisa ser numérico para usar NUMERIC_INCREMENT");
        }
        clone.put(field, ((Number) val).longValue() + index);
    }

    private void applyFixedValues(Map<String, Object> clone, String field, List<String> values, int index) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("FIXED_VALUES requer uma lista 'values' não vazia para o campo '" + field + "'");
        }
        // Circular: se tiver 3 values e 5 clones, volta ao início
        clone.put(field, values.get((index - 1) % values.size()));
    }


}
