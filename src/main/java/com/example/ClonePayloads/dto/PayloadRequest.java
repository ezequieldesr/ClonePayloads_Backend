package com.example.ClonePayloads.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadRequest {
    @Min(value = 2, message = "A quantidade tem que ser maior ou igual a 2")
    @Max(value = 100, message = "A quantidade máxima permitida é 100")
    private int quantidade;

    @NotNull(message = "O payload não pode ser nulo")
    @NotEmpty(message = "O payload não pode estar vazio")
    private Map<String, Object> payload;

    private List<FieldRole> fieldRoles;
}
