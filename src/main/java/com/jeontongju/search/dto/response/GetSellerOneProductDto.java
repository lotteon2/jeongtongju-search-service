package com.jeontongju.search.dto.response;

import com.jeontongju.search.document.Product;
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

  private Boolean isActivate;

  public static GetSellerOneProductDto toDto(Product product) {
    return GetSellerOneProductDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .totalSalesCount(product.getTotalSalesCount())
        .productPrice(product.getPrice())
        .stockQuantity(product.getStockQuantity())
        .reviewCount(product.getReviewCount())
        .shortsId(product.getShortsId())
        .isActivate(product.getIsActivate())
        .build();
  }
}
