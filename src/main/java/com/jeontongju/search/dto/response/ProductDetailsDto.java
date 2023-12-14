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
public class ProductDetailsDto {

  private String productId;

  private String productName;

  private String productDescription;

  private String productThumbnailImageUrl;

  private Double productAlcoholDegree;

  private Long productCapacity;

  private String breweryName;

  private String breweryZonecode;

  private String breweryAddress;

  private String breweryAddressDetails;

  private String manufacturer;

  private Long productPrice;

  private Long capacityToPriceRatio;

  private Long registeredQuantity;

  private String productDetailsImageUrl;

  private Long categoryId;

  private Long sellerId;

  private Taste taste;

  private List<String> rawMaterial;

  private List<String> food;

  private List<String> concept;

  private Boolean isSoldOut;

  private Boolean isLikes;

  public static ProductDetailsDto toDto(Product product, Boolean isLikes) {

    return ProductDetailsDto.builder()
        .productId(product.getProductId())
        .productName(product.getName())
        .productDescription(product.getDescription())
        .productThumbnailImageUrl(product.getProductThumbnailImageUrl())
        .productAlcoholDegree(product.getAlcoholDegree())
        .productCapacity(product.getCapacity())
        .breweryName(product.getBreweryName())
        .breweryZonecode(product.getBreweryZonecode())
        .breweryAddress(product.getBreweryAddress())
        .breweryAddressDetails(product.getBreweryAddressDetails())
        .manufacturer(product.getManufacturer())
        .productPrice(product.getPrice())
        .capacityToPriceRatio(product.getCapacityToPriceRatio())
        .registeredQuantity(product.getStockQuantity())
        .productDetailsImageUrl(product.getProductDetailsImageUrl())
        .categoryId(product.getCategoryId())
        .sellerId(product.getSellerId())
        .taste(product.getTaste())
        .rawMaterial(product.getRawMaterial())
        .food(product.getFood())
        .concept(product.getConcept())
        .isSoldOut(product.getStockQuantity() <= 0)
        .isLikes(isLikes)
        .build();
  }
}
