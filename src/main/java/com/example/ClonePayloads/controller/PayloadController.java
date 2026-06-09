package com.example.ClonePayloads.controller;

import com.example.ClonePayloads.dto.PayloadRequest;
import com.example.ClonePayloads.service.PayloadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://clone-payloads.vercel.app/")
public class PayloadController {

    private final PayloadService payloadService;

    public PayloadController(PayloadService payloadService) {
        this.payloadService = payloadService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<Map<String, Object>>> generatePayloads(@Valid @RequestBody PayloadRequest payloadreq) {
            return ResponseEntity.ok(payloadService.duplicate(payloadreq).getPayloads());
    }

}
