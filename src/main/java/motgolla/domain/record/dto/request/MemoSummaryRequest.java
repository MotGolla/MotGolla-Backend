package motgolla.domain.record.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "STT 텍스트 기반 메모 요약 요청 DTO")
public class MemoSummaryRequest {

  @Schema(description = "STT 텍스트 메모", example = "이 제품은 착용감이 어,,, 매우 좋고,,, 가볍다.")
  private String sttMemo;
}
