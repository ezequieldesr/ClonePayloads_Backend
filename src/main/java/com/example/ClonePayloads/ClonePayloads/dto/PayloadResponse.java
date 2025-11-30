package com.example.ClonePayloads.ClonePayloads.dto;

import java.util.List;
import java.util.Map;

public class PayloadResponse {
    List<Map<String, Object>> payloads;

    public PayloadResponse(List<Map<String, Object>> payloads) {
        this.payloads = payloads;
    }

    public List<Map<String, Object>> getPayloads() {
        return payloads;
    }
}
