package com.jeontongju.search.client;

import com.jeontongju.search.dto.request.IsWishProductDto;
import com.jeontongju.search.dto.response.IsWishInfoDto;
import com.jeontongju.search.dto.temp.FeignFormat;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wishCartServiceClient", url = "https://e62e9111-4ca7-4984-93da-a6b562b71a83.mock.pstmn.io")
public interface WishCartServiceClient {

  @PostMapping("/products/likes")
  FeignFormat<List<IsWishInfoDto>> getIsWish(
      @RequestBody IsWishProductDto isWishProductDto);
}
