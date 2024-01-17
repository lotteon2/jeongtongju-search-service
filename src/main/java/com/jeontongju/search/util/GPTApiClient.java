package com.jeontongju.search.util;


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

  public String getProductByGPTTest(String query) {

    String tag = "";
    try{
      String responseMessage = chatgptService.sendMessage(createPrompt(query));
      if (responseMessage != null) {
        responseMessage = responseMessage.replaceAll("\\s+", "").replaceAll("[()]", "");
        log.info(responseMessage);
        ConceptTypeEnum tagByGPTEnum = ConceptTypeEnum.valueOf(responseMessage);
        tag = tagByGPTEnum.getValue();
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return tag;
  }

  private String createPrompt(String query) {

    return "Just choose only one of tags. "
        + "\n"
        + "- Tags related to the concept are (CAMPING), (FISHING), (PARTY), (GATHERING), (YEAR_END), (HIKING), (OUTING), (TRIP), (GIFT), (MEETING_THE_FAMILY), (HOLIDAY), (ROOPTOP), (HEALING), (EMOTION), (HANGOVER_REMEDY). "
            + "\n" +
            "- The format is (tag). " +
            "- Pick only one tag that is most relevant to the following sentence. " +
            "- sentence is '" +
            query
            +
            "' " +
            "\n" +
            "- Answer only the tag I gave you. " +
            "- Answer only the tags in parentheses. ";
  }
}
