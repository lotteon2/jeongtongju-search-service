package com.jeontongju.search.exception.advice;

import io.github.bitbox.bitbox.dto.ResponseFormat;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FeignClientControllerAdvice {

  private static final String FEIGN_CLIENT_EXCEPTION_MESSAGE = "FEIGN 통신 에러 ";

  @ExceptionHandler(CallNotPermittedException.class) // circuit 열렸을 때
  public ResponseEntity<ResponseFormat<Void>> handleCallNotPermittedException(
      CallNotPermittedException e) {
    log.error(e.getMessage());
    ResponseFormat<Void> body =
        ResponseFormat.<Void>builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .detail(FEIGN_CLIENT_EXCEPTION_MESSAGE)
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(body);
  }

  @ExceptionHandler(NoFallbackAvailableException.class) // fallback를 지정했는데, 없을 때
  public ResponseEntity<ResponseFormat<Void>> handleNoFallbackAvailableException(
      NoFallbackAvailableException e) {
    log.error(e.getMessage());
    ResponseFormat<Void> body =
        ResponseFormat.<Void>builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .detail(FEIGN_CLIENT_EXCEPTION_MESSAGE)
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(body);
  }
}
