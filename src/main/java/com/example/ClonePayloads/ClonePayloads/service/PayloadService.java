package com.example.ClonePayloads.ClonePayloads.service;

import com.example.ClonePayloads.ClonePayloads.dto.PayloadRequest;
import com.example.ClonePayloads.ClonePayloads.dto.PayloadResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayloadService {
    public PayloadResponse duplicate(PayloadRequest payloadRequest){
        List<Map<String, Object>> result = new ArrayList<>();

        for(int i = 0; i < payloadRequest.getQuantidade(); i++){
            result.add(generatorPayload(payloadRequest.getPayload(), i + 1));
        }
        return new PayloadResponse(result);
    }

    public Map<String, Object> generatorPayload(Map<String, Object> original, int addMillis){
        Map<String, Object> clone = new HashMap<>(original);

        Object ts = original.get("timestamp");
        if (ts == null || !(ts instanceof String) || ((String) ts).isBlank()) {
            throw new IllegalArgumentException("O timestamp tem que estar presente no payload");
        }
        String tsObj = (String) ts;
        Instant base;

        try {
            base = Instant.parse(tsObj);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("O timestamp deve estar no formato ISO-8601: " + ts);
        }

        Instant newTs = base.plusMillis(addMillis);

        clone.put("timestamp", newTs.toString());

        return clone;
    }


}
