package com.jeontongju.search.controller;

import com.jeontongju.search.dto.PageResponseFormat;
import com.jeontongju.search.dto.response.GetMyProductDto;
import com.jeontongju.search.dto.response.ProductDetailsDto;
import com.jeontongju.search.dto.temp.ResponseFormat;
import com.jeontongju.search.enums.temp.MemberRoleEnum;
import com.jeontongju.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SearchRestController {

  private final SearchService searchService;

  @GetMapping("/products/{productId}")
  public ResponseEntity<ResponseFormat<ProductDetailsDto>> getProductDetails(
      @PathVariable String productId,
      @RequestHeader(required = false) Long memberId,
      @RequestHeader(required = false) MemberRoleEnum memberRole) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<ProductDetailsDto>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("상품 상세 페이지 조회 성공")
                .data(searchService.getProductDetails(productId, memberId))
                .build());
  }

  @GetMapping("/sellers/products")
  public ResponseEntity<ResponseFormat<PageResponseFormat<List<GetMyProductDto>>>> getMyProduct(
          @RequestHeader Long memberId,
          @RequestHeader MemberRoleEnum memberRole,
          @PageableDefault(page = 0, sort = "stockQuantity", size = 10) Pageable pageable
          ) {

    return ResponseEntity.ok()
            .body(
                    ResponseFormat.<PageResponseFormat<List<GetMyProductDto>>>builder()
                            .code(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .detail("등록한 셀러 상품 목록 조회 성공")
                            .data(searchService.getMyProduct(memberId, pageable))
                            .build());
  }
}
