package com.jeontongju.search.dto.response;

import com.jeontongju.search.document.Product;
import com.jeontongju.search.document.Taste;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyProductDto {

  private String productId;

  private String productName;

  private String productThumbnailImageUrl;

  private Long totalSalesCount;

  private Long productPrice;

  private Long stockQuantity;

  private Long reviewCount;

  private Long shortsId;

  private String productDetailsImageUrl;

  private Taste taste;

  private List<String> concept;

  private List<String> rawMaterial;

  private List<String> food;

  private Boolean isActivate;

  public static GetMyProductDto toDto(Product product) {

    return GetMyProductDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .productThumbnailImageUrl(product.getProductThumbnailImageUrl())
        .totalSalesCount(product.getAccumulateTotalSalesCount())
        .productPrice(product.getPrice())
        .stockQuantity(product.getStockQuantity())
        .reviewCount(product.getReviewCount())
        .shortsId(product.getShortsId())
        .productDetailsImageUrl(product.getProductDetailsImageUrl())
        .taste(product.getTaste())
        .concept(product.getConcept())
        .rawMaterial(product.getRawMaterial())
        .food(product.getFood())
        .isActivate(product.getIsActivate())
        .build();
  }
}
