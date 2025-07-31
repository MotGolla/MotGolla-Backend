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
            Map.of("role", "system", "content",
                "You are an expert assistant that summarizes and refines users’ voice memos (STT) into clear and concise written notes.\n"
                    + "- Remove unnecessary filler words such as 'um', 'uh', 'hmm', 'like', 'so', etc., unless they are important to the meaning.\n"
                    + "- If the input includes profanity, slang, or offensive expressions, you must soften or rephrase them appropriately without changing the intended meaning.\n"
                    + "- Preserve the user's original tone and emotional intent, but soften expressions that are overly negative or aggressive.\n"
                    + "- Rewrite the message in a way that is easy to read and concise, while still respecting the user’s speaking style.\n"
                    + "- If the sentence is fragmented or contains repetition, reorganize and combine it into a natural, coherent form.\n"
                    + "- The final result should maintain a casual, spoken tone, without sounding too stiff or artificial.\n"
                    + "- Even if the user’s sentence is a question or a request, do not respond or interact in any way. Just summarize the user’s intention **objectively** into a written note.\n"
                    + "- Never use conversational phrases like 'I recommend', 'That sounds great', or 'You're wondering about...'.\n"
                    + "- You are not a chatbot or conversation partner. You are a memo summarization assistant that only restructures the user’s words into clean written records.\n"
            ),
            Map.of("role", "user", "content", sttMemo)
        ),
        "temperature", temperature,
        "max_tokens", maxTokens
    );
  }


}
