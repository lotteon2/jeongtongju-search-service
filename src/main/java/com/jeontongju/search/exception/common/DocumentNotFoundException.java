package com.jeontongju.search.exception.common;

import org.springframework.http.HttpStatus;

public class DocumentNotFoundException extends DomainException {

  public DocumentNotFoundException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
