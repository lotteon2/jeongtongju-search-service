package com.jeontongju.search.controller;

import com.jeontongju.search.dto.PageResponseFormat;
import com.jeontongju.search.dto.response.GetMyProductDto;
import com.jeontongju.search.dto.response.GetProductDto;
import com.jeontongju.search.dto.response.GetSellerOneProductDto;
import com.jeontongju.search.dto.response.ProductDetailsDto;
import com.jeontongju.search.service.SearchService;
import io.github.bitbox.bitbox.dto.ResponseFormat;
import io.github.bitbox.bitbox.enums.MemberRoleEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
  @GetMapping("/admin/sellers/{sellerId}/products")
  public ResponseEntity<ResponseFormat<PageResponseFormat<List<GetSellerOneProductDto>>>> getSellerOneProduct(
          @PathVariable Long sellerId,
          @RequestHeader Long memberId,
          @RequestHeader MemberRoleEnum memberRole,
          @PageableDefault(page = 0, sort = "createdAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable
  ) {

    return ResponseEntity.ok()
            .body(
                    ResponseFormat.<PageResponseFormat<List<GetSellerOneProductDto>>>builder()
                            .code(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .detail("셀러 상품 목록 조회 성공")
                            .data(searchService.getSellerOneProduct(sellerId, pageable))
                            .build());
  }

  @GetMapping("/sellers/{sellerId}/products/all")
  public ResponseEntity<ResponseFormat<PageResponseFormat<List<GetProductDto>>>> getAllProductAtSellerShop(
          @PathVariable Long sellerId,
          @RequestHeader(required = false) Long memberId,
          @RequestHeader(required = false) MemberRoleEnum memberRole,
          @PageableDefault(page = 0, sort = "createdAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable
  ) {

    return ResponseEntity.ok()
            .body(
                    ResponseFormat.<PageResponseFormat<List<GetProductDto>>>builder()
                            .code(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .detail("셀러 샵의 상품 목록 성공")
                            .data(searchService.getAllProductSellerShop(sellerId, pageable, memberId))
                            .build());
  }

  @GetMapping("/sellers/{sellerId}/products")
  public ResponseEntity<ResponseFormat<List<GetProductDto>>> getProductAtSellerShop(
          @PathVariable Long sellerId,
          @RequestHeader(required = false) Long memberId,
          @RequestHeader(required = false) MemberRoleEnum memberRole,
          @PageableDefault(page = 0, sort = "totalSalesCount", direction = Sort.Direction.DESC, size = 5) Pageable pageable
  ) {

    return ResponseEntity.ok()
            .body(
                    ResponseFormat.<List<GetProductDto>>builder()
                            .code(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .detail("셀러 샵의 상품 목록 성공")
                            .data(searchService.getProductSellerShop(sellerId, pageable, memberId))
                            .build());
  }

  @GetMapping("/products/categories")
  public ResponseEntity<ResponseFormat<PageResponseFormat<List<GetProductDto>>>> getProductByCategory(
          @RequestHeader(required = false) Long memberId,
          @RequestHeader(required = false) MemberRoleEnum memberRole,
          @RequestParam Long id,
          @PageableDefault(page = 0, sort = "createdAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable
  ) {

    return ResponseEntity.ok()
            .body(
                    ResponseFormat.<PageResponseFormat<List<GetProductDto>>>builder()
                            .code(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .detail("카테고리 별 상품 목록 성공")
                            .data(searchService.getProductByCategory(id, pageable, memberId))
                            .build());
  }

  @GetMapping("/products/search")
  public ResponseEntity<ResponseFormat<PageResponseFormat<List<GetProductDto>>>> getProductBySearch(
          @RequestHeader(required = false) Long memberId,
          @RequestHeader(required = false) MemberRoleEnum memberRole,
          @RequestParam String query,
          @PageableDefault(page = 0, sort = "_score", direction = Sort.Direction.DESC, size = 10) Pageable pageable
  ) {

    return ResponseEntity.ok()
            .body(
                    ResponseFormat.<PageResponseFormat<List<GetProductDto>>>builder()
                            .code(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .detail("상품 검색 성공")
                            .data(searchService.getProductBySearch(query, pageable, memberId))
                            .build());
  }
}
