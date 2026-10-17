package com.example.ClonePayloads.dto;

import java.util.List;
import jakarta.validation.constraints.*;

@lombok.Data
public class FieldRole {
   @NotBlank @Size(max=200)
   private String field;
    @NotBlank @Pattern(regexp="TIMESTAMP_INCREMENT|NUMERIC_INCREMENT|UUID|FIXED_VALUES")
    private String strategy;
    @Size(max=100)
    private List<@NotNull @Size(max=1000) String> values;

    public String getField() {
        return field;
    }

    public String getStrategy() {
        return strategy;
    }

    public List<String> getValues() {
        return values;
    }
}
