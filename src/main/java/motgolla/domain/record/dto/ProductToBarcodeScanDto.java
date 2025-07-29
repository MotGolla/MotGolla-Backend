package motgolla.domain.record.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductToBarcodeScanDto {
  private String brand;
  private Long product_id;
  private String product_name;
  private String product_number;
}
