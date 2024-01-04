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
public class GetProductAutoDto {

  String productId;
  String productName;
  String productThumbnailImageUrl;

  public static GetProductAutoDto toDto(Product product) {
    return GetProductAutoDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .productThumbnailImageUrl(product.getProductThumbnailImageUrl())
        .build();
  }
}
