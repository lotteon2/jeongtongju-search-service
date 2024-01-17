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
public class GetProductDto {

  private String productId;
  private String productName;
  private String productDescription;
  private String productThumbnailImageUrl;
  private Long productPrice;
  private Long reviewCount;
  private Long capacityToPriceRatio;
  private Boolean isLikes;
  private Boolean isSoldOut;

  public static GetProductDto toDto(Product product, Boolean isLikes) {
    return GetProductDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .productDescription(product.getDescription())
        .productThumbnailImageUrl(product.getProductThumbnailImageUrl())
        .productPrice(product.getPrice())
        .reviewCount(product.getReviewCount())
        .capacityToPriceRatio(product.getCapacityToPriceRatio())
        .isSoldOut(product.getStockQuantity() <= 0)
        .isLikes(isLikes != null ? isLikes : false)
        .build();
  }
}
