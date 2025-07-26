package motgolla.domain.record.mapper;

import java.util.Optional;
import motgolla.domain.record.dto.ProductToBarcodeScanDto;
import motgolla.domain.record.dto.request.RecordRegisterRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface RecordMapper {

  // memberId == createBy
  void insertRecord(@Param("request") RecordRegisterRequest recordRegisterRequest,
      @Param("memberId") Long memberId,
      @Param("departmentStoreBrandId") Long departmentStoreBrandId);

  // 기록 사진 저장
  void insertRecordImage(@Param("imageUrl") String imageUrl,
      @Param("imageType") String imageType,
      @Param("recordId") Long recordId,
      @Param("memberId") Long memberId);

  // 현재 백화점 찾기
  Long findDepartmentStoreByName(@Param("name") String departmentStoreName);

  // 바코드로 OCR 정보 찾기
  Optional<ProductToBarcodeScanDto> findBarcodeScanInfo(@Param("barcode") String barcodeNumber);

  // 현재 백화점에 있는 브랜드 찾기
  Long findDepartmentStoreBrand(@Param("brand") String brandName,
      @Param("departmentStoreId") Long departmentStoreId);
}
