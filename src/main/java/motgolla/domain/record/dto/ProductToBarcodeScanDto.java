package motgolla.domain.record.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductToBarcodeScanDto {
  private String brand;
  private Long productId;
  private String productName;
  private String productNumber;


}
