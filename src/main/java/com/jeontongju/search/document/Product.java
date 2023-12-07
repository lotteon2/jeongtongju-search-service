package com.jeontongju.search.document;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

  private String productId;

  private String name;

  private String description;

  private String productThumbnailImageUrl;

  private Double alcoholDegree;

  private Long capacity;

  private String breweryName;

  private String breweryZonecode;

  private String breweryAddress;

  private String breweryAddressDetails;

  private String manufacturer;

  private Long price;

  private Long capacityToPriceRatio;

  private Long stockQuantity;

  private Long reviewCount;

  private Long totalSalesCount;

  private String storeName;

  private String storeImageUrl;

  private String productDetailsImageUrl;

  private Long categoryId;

  private Long sellerId;

  private Long shortsId;

  private Taste taste;

  private List<String> rawMaterial;

  private List<String> food;

  private List<String> concept;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Boolean isActivate;

  private Boolean isDeleted;
}
