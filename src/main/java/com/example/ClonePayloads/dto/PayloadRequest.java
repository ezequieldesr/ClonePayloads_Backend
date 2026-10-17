package com.example.ClonePayloads.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, message = "O payload não pode estar vazio")
    @Size(max = 100, message = "A quantidade máxima de campos no payload é 100")
    private Map<String, Object> payload;

    @Valid
    @Size(max = 100, message = "A quantidade máxima de regras é 100")
    private List<@NotNull FieldRole> fieldRoles;
}
