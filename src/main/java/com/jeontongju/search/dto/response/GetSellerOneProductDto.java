package com.jeontongju.search.dto.response;

import com.jeontongju.search.document.Product;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetSellerOneProductDto {

  private String productId;

  private String productName;

  private Long totalSalesCount;

  private Long productPrice;

  private Long stockQuantity;

  private Long reviewCount;

  private Long shortsId;

  private Long sellerId;

  private String storeName;

  private Boolean isActivate;

  private LocalDateTime createdAt;

  public static GetSellerOneProductDto toDto(Product product) {
    return GetSellerOneProductDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .totalSalesCount(product.getAccumulateTotalSalesCount())
        .productPrice(product.getPrice())
        .stockQuantity(product.getStockQuantity())
        .reviewCount(product.getReviewCount())
        .shortsId(product.getShortsId())
        .sellerId(product.getSellerId())
        .storeName(product.getStoreName())
        .isActivate(product.getIsActivate())
        .createdAt(product.getCreatedAt())
        .build();
  }
}
