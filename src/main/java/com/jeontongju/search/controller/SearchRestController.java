package com.jeontongju.search.controller;

import com.jeontongju.search.dto.response.*;
import com.jeontongju.search.service.SearchService;
import io.github.bitbox.bitbox.dto.ResponseFormat;
import io.github.bitbox.bitbox.enums.MemberRoleEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
  public ResponseEntity<ResponseFormat<Page<GetMyProductDto>>> getMyProduct(
      @RequestHeader Long memberId,
      @RequestHeader MemberRoleEnum memberRole,
      @PageableDefault(page = 0, sort = "stockQuantity", size = 10) Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Page<GetMyProductDto>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("등록한 셀러 상품 목록 조회 성공")
                .data(searchService.getMyProduct(memberId, pageable))
                .build());
  }

  @GetMapping("/admin/sellers/{sellerId}/products")
  public ResponseEntity<ResponseFormat<Page<GetSellerOneProductDto>>>
      getSellerOneProduct(
          @PathVariable Long sellerId,
          @RequestHeader Long memberId,
          @RequestHeader MemberRoleEnum memberRole,
          @PageableDefault(page = 0, sort = "createdAt", direction = Sort.Direction.DESC, size = 10)
              Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Page<GetSellerOneProductDto>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("셀러 상품 목록 조회 성공")
                .data(searchService.getSellerOneProduct(sellerId, pageable))
                .build());
  }

  @GetMapping("/sellers/{sellerId}/products/all")
  public ResponseEntity<ResponseFormat<Page<GetProductDto>>>
      getAllProductAtSellerShop(
          @PathVariable Long sellerId,
          @RequestHeader(required = false) Long memberId,
          @RequestHeader(required = false) MemberRoleEnum memberRole,
          @PageableDefault(page = 0, sort = "createdAt", direction = Sort.Direction.DESC, size = 10)
              Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Page<GetProductDto>>builder()
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
      @PageableDefault(
              page = 0,
              sort = "totalSalesCount",
              direction = Sort.Direction.DESC,
              size = 5)
          Pageable pageable) {

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
  public ResponseEntity<ResponseFormat<Page<GetProductDto>>>
      getProductByCategory(
          @RequestHeader(required = false) Long memberId,
          @RequestHeader(required = false) MemberRoleEnum memberRole,
          @RequestParam Long id,
          @PageableDefault(page = 0, sort = "createdAt", direction = Sort.Direction.DESC, size = 10)
              Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Page<GetProductDto>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("카테고리 별 상품 목록 성공")
                .data(searchService.getProductByCategory(id, pageable, memberId))
                .build());
  }

  @GetMapping("/products/search")
  public ResponseEntity<ResponseFormat<Page<GetProductDto>>> getProductBySearch(
      @RequestHeader(required = false) Long memberId,
      @RequestHeader(required = false) MemberRoleEnum memberRole,
      @RequestParam String query,
      @PageableDefault(page = 0, sort = "_score", direction = Sort.Direction.DESC, size = 10)
          Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<Page<GetProductDto>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("상품 검색 성공")
                .data(searchService.getProductBySearch(query, pageable, memberId))
                .build());
  }

  @GetMapping("/products/cereal-crops")
  public ResponseEntity<ResponseFormat<GetCerealCropsProductDto>> getCerealCropsProduct(
      @RequestHeader(required = false) Long memberId,
      @RequestHeader(required = false) MemberRoleEnum memberRole,
      @PageableDefault(sort = "totalSalesCount", direction = Sort.Direction.DESC, size = 10)
          Pageable pageable) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<GetCerealCropsProductDto>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .detail("메인 페이지 구황작물 조회 성공")
                .data(searchService.getCerealCropsProduct(pageable, memberId))
                .build());
  }

  @GetMapping("/products")
  public ResponseEntity<ResponseFormat<List<GetMainProductDto>>> getProduct(
          @RequestHeader(required = false) Long memberId,
          @RequestHeader(required = false) MemberRoleEnum memberRole,
          @PageableDefault(sort = "capacityToPriceRatio", direction = Sort.Direction.ASC, size = 6)
          Pageable pageable) {

    return ResponseEntity.ok()
            .body(
                    ResponseFormat.<List<GetMainProductDto>>builder()
                            .code(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .detail("상품 조회 성공")
                            .data(searchService.getProduct(pageable, memberId))
                            .build());
  }

    @GetMapping("/products/holiday")
    public ResponseEntity<ResponseFormat<List<GetMainProductDto>>> getHolidayProduct(
            @RequestHeader(required = false) Long memberId,
            @RequestHeader(required = false) MemberRoleEnum memberRole,
            @PageableDefault(sort = "totalSalesCount", direction = Sort.Direction.DESC, size = 6)
            Pageable pageable) {

        return ResponseEntity.ok()
                .body(
                        ResponseFormat.<List<GetMainProductDto>>builder()
                                .code(HttpStatus.OK.value())
                                .message(HttpStatus.OK.name())
                                .detail("설날 전통주 조회 성공")
                                .data(searchService.getHolidayProduct(pageable, memberId))
                                .build());
    }

    @GetMapping("/products/search/auto")
    public ResponseEntity<ResponseFormat<List<GetProductAutoDto>>> getProductByAutoSearch(
            @RequestParam String query) {

        return ResponseEntity.ok()
                .body(
                        ResponseFormat.<List<GetProductAutoDto>>builder()
                                .code(HttpStatus.OK.value())
                                .message(HttpStatus.OK.name())
                                .detail("상품 자동 완성 성공")
                                .data(searchService.getProductByAutoSearch(query))
                                .build());
    }

    @GetMapping("/products/all")
    public ResponseEntity<ResponseFormat<Page<GetProductDto>>> getAllProduct(
            @RequestHeader(required = false) Long memberId,
            @RequestHeader(required = false) MemberRoleEnum memberRole,
            @RequestParam(required = false) Long price,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 6)
            Pageable pageable) {

        return ResponseEntity.ok()
                .body(
                        ResponseFormat.<Page<GetProductDto>>builder()
                                .code(HttpStatus.OK.value())
                                .message(HttpStatus.OK.name())
                                .detail("모든 상품 조회 성공")
                                .data(searchService.getAllProduct(pageable, memberId))
                                .build());
    }
}
