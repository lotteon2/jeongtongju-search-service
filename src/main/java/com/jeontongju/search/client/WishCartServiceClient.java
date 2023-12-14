package com.jeontongju.search.client;

import io.github.bitbox.bitbox.dto.FeignFormat;
import io.github.bitbox.bitbox.dto.IsWishProductDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.HashMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wish-cart-service")
public interface WishCartServiceClient {

  @CircuitBreaker(
          name = "wishCartServiceClient@getIsWish",
          fallbackMethod = "getIsWishFallbackMethod"
  )
  @PostMapping("/products/likes")
  FeignFormat<HashMap<String, Boolean>> getIsWish(@RequestBody IsWishProductDto isWishProductDto);

  default FeignFormat<HashMap<String, Boolean>> getIsWishFallbackMethod(@RequestBody IsWishProductDto isWishProductDto, Throwable t) {

    HashMap<String, Boolean> result = new HashMap<>();
    isWishProductDto.getProductIds().stream().forEach(id -> result.put(id, false));

    return FeignFormat.<HashMap<String, Boolean>>builder()
            .code(HttpStatus.OK.value())
            .data(result)
            .build();

  }
}
