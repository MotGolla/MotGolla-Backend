package motgolla.domain.record.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Schema(description = "사용자 월별 쇼핑 기록 조회 Request")
public class RecordDateFilterRequest {

  @Schema(description = "조회할 월 (형식: YYYY-MM)", example = "2025-07")
  private String month;
}