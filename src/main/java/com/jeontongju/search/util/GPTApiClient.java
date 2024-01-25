package com.jeontongju.search.util;

import com.jeontongju.search.dto.PriceAlcoholDto;
import com.jeontongju.search.enums.temp.ConceptTypeEnum;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GPTApiClient {

  private final ChatgptService chatgptService;

  public String getProductConceptByGPT(String query) {

    String tag = "";
    try {
      String responseMessage = chatgptService.sendMessage(createConceptPrompt(query));
      if (responseMessage != null) {
        responseMessage = responseMessage.replaceAll("\\s+", "").replaceAll("[()]", "");
        log.info("gptConceptResponse - " + responseMessage);
        ConceptTypeEnum tagByGPTEnum = ConceptTypeEnum.valueOf(responseMessage);
        tag = tagByGPTEnum.getValue();
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return tag;
  }

  public PriceAlcoholDto getProductFilteringByGPT(String query) {

    PriceAlcoholDto priceAlcoholDto = new PriceAlcoholDto();
    try {
      String responseMessage = chatgptService.sendMessage(createFilteringPrompt(query));
      if (responseMessage != null) {

        String response = responseMessage.replaceAll("\\s+", "").replaceAll("[()]", "");
        log.info("gptFilteringResponse - " +  response);
        if (response.contains("/")) {
          String[] responseList = response.split("/");
          if (responseList.length == 2 && responseList[0].contains(":")) {
            String[] priceList = responseList[0].split(":");
            if (priceList.length == 2) {
              priceAlcoholDto.setMinPrice(Long.valueOf(priceList[0]));
              priceAlcoholDto.setMaxPrice(Long.valueOf(priceList[1]));
            }
          }
          if (responseList.length == 2 && responseList[1].contains(":")) {
            String[] alcoholList = responseList[1].split(":");
            if (alcoholList.length == 2) {
              priceAlcoholDto.setMinAlcohol(Double.valueOf(alcoholList[0]));
              priceAlcoholDto.setMaxAlcohol(Double.valueOf(alcoholList[1]));
            }
          }
        }

      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return priceAlcoholDto;
  }

  private String createConceptPrompt(String query) {

    return "Just choose only one of tags. "
        + "\n"
        + "- Tags related to the concept are (CAMPING), (FISHING), (PARTY), (GATHERING), (YEAR_END), (HIKING), (OUTING), (TRIP), (GIFT), (MEETING_THE_FAMILY), (HOLIDAY), (ROOPTOP), (HEALING), (EMOTION), (HANGOVER_REMEDY) . "
        + "\n"
        + "- The format is (tag). "
        + "- Pick only one tag that is most relevant to the following sentence. "
        + "- sentence is '"
        + query
        + "' "
        + "\n"
        + "- Answer only the tag I gave you. "
        + "- Answer only the tags in parentheses. ";
  }

  private String createFilteringPrompt(String query) {

    return "Regarding the following question sentence, please answer according to the rules I mentioned below. "
        + "\n"
        + "- question sentence is '"
        + query
        + "' "
        + "\n"
        + "You have to answer prices, and alcoholDegrees that is most relevant to the question sentence. "
        + "\n"
        + "If there is a distinction between the minimum and maximum frequencies of the product's alcohol in the question sentence, please tell me the minimum and maximum frequencies. "
        + "If there is a distinction between the minimum and maximum prices of a product in the question sentence, please tell me the minimum and maximum prices. "
        + "\n"
        +  "Just tell me the numbers in (minAlcoholDegree:maxAlcoholDegree/minPrice:maxPrice) format. The answer is only one line. "
        + "If there is no minPrice, say -1 . If there is no maxPrice, say -1 . "
        + "If there is no minAlcoholDegree, say -1 . If there is no maxAlcoholDegree, say -1 .  "
        + "In the first line, Just tell me the numbers in (minPrice:maxPrice)"
        + "\n"
        + "- Only the choices I gave you without explanation, tell me according to the rules. ";
  }
}
