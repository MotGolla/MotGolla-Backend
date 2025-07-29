package motgolla.domain.record.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "날짜 및 카테고리 기반 상품 응답 Response")
public class RecordProductFilterResponse {


  @Schema(description = "기록 ID", example = "42")
  @JsonProperty("record_id")
  private Long recordId;

  @Schema(description = "기록 상태", example = "AVAILABLE")
  @JsonProperty("state")
  private String state;

  @Schema(description = "대표 이미지 URL", example = "https://example.com/image.jpg")
  @JsonProperty("img_url")
  private String imgUrl;

  @Schema(description = "상품명", example = "크롭 반팔티")
  @JsonProperty("product_name")
  private String productName;

  @Schema(description = "브랜드명", example = "무신사 스탠다드")
  @JsonProperty("brand_name")
  private String brandName;

  @Schema(description = "브랜드 층수", example = "3F")
  @JsonProperty("brand_floor")
  private String brandFloor;

  @Schema(description = "상품 가격", example = "12,900원")
  @JsonProperty("price")
  private String productPrice;
}
