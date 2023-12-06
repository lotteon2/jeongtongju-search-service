package com.jeontongju.search.exception.common;

import org.springframework.http.HttpStatus;

public class InvalidPermissionException extends RuntimeException {

  private static final String message = "잘못된 접근입니다.";

  public InvalidPermissionException() {
    super(message);
  }

  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
