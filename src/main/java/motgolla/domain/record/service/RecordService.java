package motgolla.domain.record.service;

import java.util.List;
import motgolla.domain.record.dto.ProductToBarcodeScanDto;
import motgolla.domain.record.dto.request.RecordProductFilterRequest;
import motgolla.domain.record.dto.request.RecordRegisterRequest;
import motgolla.domain.record.dto.response.RecordProductFilterResponse;
import org.apache.ibatis.annotations.Param;
import motgolla.domain.record.dto.response.RecordDetailResponse;

public interface RecordService {

  void registerRecord(RecordRegisterRequest recordRegisterRequest, Long memberId);

  ProductToBarcodeScanDto confirmProductByBarcode(String barcode, Long departmentStoreId);

  RecordDetailResponse getRecordDetail(Long recordId);

  List<RecordProductFilterResponse> getProductsByCursor(Long memberId,
      RecordProductFilterRequest request);

  void updateRecordStatus(
       Long memberId,
       Long recordId,
       String status);

  public List<String> findRecordDatesByYearMonth(Long memberId, String yearMonth);
}

