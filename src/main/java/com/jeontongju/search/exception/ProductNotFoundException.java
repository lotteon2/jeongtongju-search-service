package com.jeontongju.search.exception;

import com.jeontongju.search.exception.common.DocumentNotFoundException;

public class ProductNotFoundException extends DocumentNotFoundException {

  private static final String message = "상품을 찾을 수 없습니다";

  public ProductNotFoundException() {
    super(message);
  }
}
