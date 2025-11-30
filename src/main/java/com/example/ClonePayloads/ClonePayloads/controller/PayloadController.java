package com.example.ClonePayloads.ClonePayloads.controller;

import com.example.ClonePayloads.ClonePayloads.dto.PayloadRequest;
import com.example.ClonePayloads.ClonePayloads.dto.PayloadResponse;
import com.example.ClonePayloads.ClonePayloads.service.PayloadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PayloadController {

    private final PayloadService payloadService;

    public PayloadController(PayloadService payloadService) {
        this.payloadService = payloadService;
    }

    @PostMapping("/generate")
    public ResponseEntity<PayloadResponse> generatePayloads(@Valid @RequestBody PayloadRequest payloadreq) {
            return ResponseEntity.ok(payloadService.duplicate(payloadreq));
    }

}
