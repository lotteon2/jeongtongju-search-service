package com.jeontongju.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCerealCropsProductDto {

  private List<GetMainProductDto> sweetPotato;
  private List<GetMainProductDto> potato;
  private List<GetMainProductDto> corn;
}
