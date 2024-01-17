package com.jeontongju.search.dto.response;

import com.jeontongju.search.document.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMainProductDto {

  private String productId;
  private String productName;
  private String productThumbnailImageUrl;
  private Long productPrice;
  private Long capacityToPriceRatio;
  private Long reviewCount;
  private Long stockQuantity;
  private Long sellerId;
  private String storeName;
  private String storeImageUrl;
  private Boolean isLikes;

  public static GetMainProductDto toDto(Product product, Boolean isLikes) {
    return GetMainProductDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .productThumbnailImageUrl(product.getProductThumbnailImageUrl())
        .productPrice(product.getPrice())
        .capacityToPriceRatio(product.getCapacityToPriceRatio())
        .reviewCount(product.getReviewCount())
        .stockQuantity(product.getStockQuantity())
        .sellerId(product.getSellerId())
        .storeName(product.getStoreName())
        .storeImageUrl(product.getStoreImageUrl())
        .isLikes(isLikes)
        .build();
  }
}
