package com.jeontongju.search.dto.response;

import com.jeontongju.search.document.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GetBestProductDto {
  private String productId;
  private String productName;
  private String productThumbnailImageUrl;

  public static GetBestProductDto toDto(Product product) {
    return GetBestProductDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .productThumbnailImageUrl(product.getProductThumbnailImageUrl())
        .build();
  }
}
