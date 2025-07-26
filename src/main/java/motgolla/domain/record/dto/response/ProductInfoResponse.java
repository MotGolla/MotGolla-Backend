package motgolla.domain.record.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import motgolla.domain.record.dto.ProductToBarcodeScanDto;

@Getter
@AllArgsConstructor
public class ProductInfoResponse {
  private String brand;
  private Long productId;
  private String productName;
  private String productNumber;

  public ProductInfoResponse(ProductToBarcodeScanDto productToBarcodeScanDto) {
    this.brand = productToBarcodeScanDto.getBrand();
    this.productId = productToBarcodeScanDto.getProductId();
    this.productName = productToBarcodeScanDto.getProductName();
    this.productNumber = productToBarcodeScanDto.getProductNumber();
  }

}
