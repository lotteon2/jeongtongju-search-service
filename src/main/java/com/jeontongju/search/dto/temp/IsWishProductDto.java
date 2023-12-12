package com.jeontongju.search.dto.temp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 domain : wish-cart, product
 detail : 상품 조회 시, 찜 유무 반환
 method : Feign
 comment :
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IsWishProductDto {

  private Long consumerId;
  private List<String> productIds;
}
