package motgolla.infra.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;
import motgolla.infra.openai.dto.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiVisionClient {

  @Value("${openai.api-key}")
  private String apiKey;

  private final WebClient webClient = WebClient.builder()
      .baseUrl("https://api.openai.com/v1")
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .build();


  /**
   * 순수 텍스트 프롬프트를 기반으로 OpenAI GPT 모델에게 요청을 보내고, 생성된 응답 텍스트(메모 요약 정보)를 반환
   *
   * @param sttMemo GPT 모델에게 전달할 프롬프트 텍스트
   * @return GPT 응답에서 추출한 텍스트 (예: 음성 메모 정리 등)
   * @throws BusinessException OpenAI 응답 오류 또는 파싱 실패 시 예외 발생
   *                           <p>
   *                           temperature: 응답의 창의성과 다양성을 조절하는 값. 0.0에 가까울수록 예측 가능한 응답, 1.0에 가까울수록
   *                           창의적인 응답이 생성 max_tokens: 응답 텍스트의 최대 길이 temperature와 maxTokens는 향후 필요에
   *                           따라 조절 가능
   */
  public String chatWithMemo(String sttMemo) {
    Map<String, Object> requestBody = createTextRequestBody(sttMemo, 0.2, 300);
    return getContent("/chat/completions", requestBody);
  }


  private String getContent(String uri, Map<String, Object> requestBody) {
    try {
      OpenAiResponse response = webClient.post()
          .uri(uri)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
          .bodyValue(requestBody)
          .retrieve()
          .bodyToMono(OpenAiResponse.class)
          .block();

      if (response == null) {
        log.error("OpenAI 응답이 null입니다. 요청 본문: {}", requestBody);
        throw new BusinessException(ErrorCode.OPENAI_RESPONSE_ERROR);
      }

      if (response.getChoices() == null || response.getChoices().isEmpty()) {
        log.error("OpenAI 응답 choices가 비어 있습니다. 전체 응답: {}", response);
        throw new BusinessException(ErrorCode.OPENAI_RESPONSE_ERROR);
      }

      String content = response.getChoices().get(0).getMessage().getContent();

      if (content == null || content.isBlank()) {
        log.error("OpenAI 응답 content가 비어 있습니다. 전체 응답: {}", response);
        throw new BusinessException(ErrorCode.OPENAI_RESPONSE_ERROR);
      }

      return content;

    } catch (WebClientResponseException e) {
      log.error("OpenAI API 호출 실패 - 상태 코드: {}, 응답 본문: {}", e.getRawStatusCode(),
          e.getResponseBodyAsString(), e);
      throw new BusinessException(ErrorCode.OPENAI_API_CALL_FAILED);
    } catch (Exception e) {
      log.error("OpenAI API 호출 중 예외 발생: {}", e.getMessage(), e);
      throw new BusinessException(ErrorCode.OPENAI_API_CALL_FAILED);
    }
  }

  private Map<String, Object> createTextRequestBody(String sttMemo, double temperature,
      int maxTokens) {
    return Map.of(
        "model", "gpt-4o",
        "messages", List.of(
            Map.of("role", "system", "content", "너는 사용자의 음성 메모(STT)를 자연스럽고 간결한 문장으로 정리하는 전문가야.\n"
                + "- '음', '어', '으음', '그니까', '오', '아' 등 불필요한 감탄사나 중복 말버릇은 제거하되, 문맥상 자연스러운 경우는 살리면 좋겠어.\n"
                + "- 욕설, 비속어, 비하 표현은 반드시 순화하거나 적절한 대체어로 바꿔서 원래 의미를 해치지 말고 전달해.\n"
                + "- 감정이나 느낌은 그대로 살리되, 지나치게 부정적이거나 공격적인 표현은 부드럽게 다듬어줘.\n"
                + "- 문장은 원래 사용자의 말투를 존중하면서도 간결하고 읽기 좋게 재구성해줘.\n"
                + "- 불완전하거나 중복되는 문장은 통합하여 자연스러운 문장으로 만들어줘.\n"
                + "- 최종 결과는 구어체 느낌을 유지하되, 공식 문서처럼 딱딱하지 않게 작성해줘.\n"),
            Map.of("role", "user", "content", sttMemo)
        ),
        "temperature", temperature,
        "max_tokens", maxTokens
    );
  }


}
