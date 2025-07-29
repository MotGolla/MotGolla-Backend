package motgolla.domain.record.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "요약된 메모 응답 DTO")
public class MemoSummaryResponse {

  @Schema(description = "요약된 메모 결과", example = "착용감이 좋고 만족스러운 제품입니다.")
  private String summary;
}
