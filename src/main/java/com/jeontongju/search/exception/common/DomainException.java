package com.jeontongju.search.exception.common;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class DomainException extends RuntimeException {
  private final Map<String, String> validation = new HashMap<>();

  public DomainException(String message) {
    super(message);
  }

  public abstract HttpStatus getStatus();

  public void addValidation(String fieldName, String errorMessage) {
    validation.put(fieldName, errorMessage);
  }
}
