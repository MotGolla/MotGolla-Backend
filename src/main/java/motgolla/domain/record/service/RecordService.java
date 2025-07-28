package motgolla.domain.record.service;

import motgolla.domain.record.dto.ProductToBarcodeScanDto;
import motgolla.domain.record.dto.request.RecordRegisterRequest;
import motgolla.domain.record.dto.response.RecordDetailResponse;

public interface RecordService {

  void registerRecord(RecordRegisterRequest recordRegisterRequest ,Long memberId);

  ProductToBarcodeScanDto confirmProductByBarcode(String barcode);

  RecordDetailResponse getRecordDetail(Long recordId);
}
