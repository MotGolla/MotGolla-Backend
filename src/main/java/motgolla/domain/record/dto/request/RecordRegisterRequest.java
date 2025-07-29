package motgolla.domain.record.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Schema(description = "기록 등록 요청 Request")
public class RecordRegisterRequest {

  @Schema(description = "기록 ID", example = "빈값",hidden = true)
  private Long id;

  @Schema(description = "현재 백화점명", example = "현대백화점 판교점")
  private String departmentStore;

  @Schema(description = "택 이미지", example = "tag.jpg")
  private MultipartFile tagImg;


  @Schema(description = "상품 이미지 목록", example = "[\"product1.jpg\", \"product2.jpg\"]")
  private List<MultipartFile> productImgs;

  @Schema(description = "브랜드명", example = "Nike")
  private String brandName;

  @Schema(description = "상품 식별번호", example = "1")
  private Long productId;

  @Schema(description = "제품명", example = "Air Max 270")
  private String productName;

  @Schema(description = "제품번호", example = "AM270-1234")
  private String productNumber;

  @Schema(description = "제품사이즈", example = "S")
  private String productSize;

  @Schema(description = "메모", example = "착화감이 좋음")
  private String noteSummary;
}
