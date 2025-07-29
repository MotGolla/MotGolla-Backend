package motgolla.domain.record.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import motgolla.domain.record.dto.ProductToBarcodeScanDto;

@Getter
@AllArgsConstructor
@Schema(description = "바코드 인식 후 반환되는 상품 정보 응답 DTO")
public class ProductInfoResponse {

  @Schema(description = "브랜드명", example = "Nike")
  private String brand;

  @Schema(description = "상품 ID", example = "1")
  private Long productId;

  @Schema(description = "상품명", example = "Air Max 270")
  private String productName;

  @Schema(description = "제품 번호", example = "AM270-1234")
  private String productNumber;

  public ProductInfoResponse(ProductToBarcodeScanDto productToBarcodeScanDto) {
    this.brand = productToBarcodeScanDto.getBrand();
    this.productId = productToBarcodeScanDto.getProduct_id();
    this.productName = productToBarcodeScanDto.getProduct_name();
    this.productNumber = productToBarcodeScanDto.getProduct_number();
  }
}
