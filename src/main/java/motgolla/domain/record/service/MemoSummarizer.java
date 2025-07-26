package motgolla.domain.record.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;
import motgolla.infra.openai.OpenAiVisionClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemoSummarizer {

  private final OpenAiVisionClient openAiVisionClient;

  public String analyze(String sttMemo) {
    String result = String.valueOf(openAiVisionClient.chatWithMemo(sttMemo));
    return result.trim();
  }

}
