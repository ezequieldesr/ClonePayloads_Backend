package com.example.ClonePayloads.dto;

import java.util.List;

public class FieldRole {
   private String field;
    private String strategy;
    private List<String> values;

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
