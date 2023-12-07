package com.jeontongju.search.controller;

import com.jeontongju.search.dto.response.ProductDetailsDto;
import com.jeontongju.search.dto.temp.ResponseFormat;
import com.jeontongju.search.enums.temp.MemberRoleEnum;
import com.jeontongju.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SearchRestController {

  private final SearchService searchService;

  @GetMapping("/products/{productId}")
  public ResponseEntity<ResponseFormat<ProductDetailsDto>> getProductDetails(
      @PathVariable String productId,
      @RequestHeader(required = false) Long memberId,
      @RequestHeader(required = false) MemberRoleEnum memberRoleEnum) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<ProductDetailsDto>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("상품 상세 페이지 조회 성공")
                .data(searchService.getProductDetails(productId, memberId))
                .build());
  }
}
