package motgolla.domain.record.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "날짜 및 카테고리 기반 커서 페이징 상품 조회 Request")
public class RecordProductFilterRequest {

  @Schema(description = "조회할 날짜 (yyyy-MM-dd 형식)", example = "2025-07-27")
  private String date;

  @Schema(description = "상품 카테고리", example = "TOP")
  private String category;

  @Schema(description = "커서 (마지막으로 받은 기록 ID)", example = "10")
  private Long cursor;

  @Schema(description = "한 번에 가져올 개수 (limit)", example = "10", defaultValue = "10")
  private Integer limit;
}
