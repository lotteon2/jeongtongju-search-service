package com.jeontongju.search.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PriceAlcoholDto {

    private Long minPrice;
    private Long maxPrice;
    private Double minAlcohol;
    private Double maxAlcohol;


}
