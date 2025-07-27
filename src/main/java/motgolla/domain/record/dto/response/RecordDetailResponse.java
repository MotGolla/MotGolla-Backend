package motgolla.domain.record.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Record 상세 조회 응답 DTO")
public class RecordDetailResponse {

    @Schema(description = "Record ID", example = "1")
    private Long recordId;

    @Schema(description = "상품 이름", example = "파인 메리노 울 4-바 클래식 카디건")
    private String productName;

    @Schema(description = "브랜드 이름", example = "톰브라운")
    private String brandName;

    @Schema(description = "상품 가격", example = "1830000")
    private Integer productPrice;

    @Schema(description = "기록 생성일시", example = "2025-07-27T14:30:00")
    private String recordCreatedAt;

    @Schema(description = "상품 사이즈", example = "L")
    private String productSize;

    @Schema(description = "상품 번호", example = "MJT306A")
    private String productNumber;

    @Schema(description = "상품 상태", example = "COMPLETED")
    private String productStatus;

    @Schema(description = "기록 요약", example = "핏이 마음에 든다")
    private String productSummary;

    @Schema(description = "브랜드 위치 정보 (복수 가능)", example = "[\"남성 3F\", \"공용 B1\"]")
    private List<String> brandLocationInfo;

    @Schema(description = "기록 이미지 URL 목록")
    private List<String> imageUrls;

    @Schema(description = "택 이미지 URL (IMAGE_TYPE = 'TAG')")
    private String tagImageUrl;

    @Schema(description = "백화점 이름", example = "현대백화점 무역센터점")
    private String storeName;

    @Schema(description = "백화점 지도 링크", example = "https://naver.me/abc123")
    private String mapLink;

}