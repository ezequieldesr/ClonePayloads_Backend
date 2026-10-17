package com.example.ClonePayloads.global;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex) {
        Map<String,Object> errors=new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e->errors.putIfAbsent(e.getField(),e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> business(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error",ex.getMessage()));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> malformed() {
        return ResponseEntity.badRequest().body(Map.of("error","JSON inválido ou estrutura incompatível com a requisição."));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> unexpected() {
        return ResponseEntity.internalServerError().body(Map.of("error","Não foi possível concluir a requisição."));
    }
}
