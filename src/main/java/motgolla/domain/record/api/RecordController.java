package motgolla.domain.record.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.member.vo.Member;
import motgolla.domain.record.dto.request.MemoSummaryRequest;
import motgolla.domain.record.dto.request.RecordRegisterRequest;
import motgolla.domain.record.dto.response.MemoSummaryResponse;
import motgolla.domain.record.dto.response.RecordRegisterResponse;
import motgolla.domain.record.service.MemoSummarizer;
import motgolla.domain.record.service.RecordService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/api/record")
@RequiredArgsConstructor
@RestController
public class RecordController {


  private final RecordService recordService;
  private final MemoSummarizer memoSummarizer;

  @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RecordRegisterResponse> recordRegister(
      @AuthenticationPrincipal Member member, @ModelAttribute RecordRegisterRequest request) {
    log.info("recordRegister() :: {}", request);
    recordService.registerRecord(request, member.getId());
    return ResponseEntity.ok().body(new RecordRegisterResponse(true, null));
  }


  // POSTMAN 으로 기록 등록하는 테스트 컨트롤러이다. 안드로이드 사용시 위에꺼 사용
  @PostMapping(value = "/test/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RecordRegisterResponse> recordRegister(
      @AuthenticationPrincipal Member member,
      @RequestPart("request") RecordRegisterRequest request,
      @RequestPart("tagImg") MultipartFile tagImg,
      @RequestPart("productImgs") List<MultipartFile> productImgs
  ) {
    // 파일과 DTO 따로 주입되므로 수동으로 세팅 필요
    request.setTagImg(tagImg);
    request.setProductImgs(productImgs);

    log.info("recordRegister() :: {}", request);
    recordService.registerRecord(request, member.getId());
    return ResponseEntity.ok().body(new RecordRegisterResponse(true, null));
  }

  @PostMapping("/memo-summary")
  public ResponseEntity<MemoSummaryResponse> summarizeMemo(
      @RequestBody MemoSummaryRequest request) {
    log.info("summarizeMemo() :: {}", request);
    String summaryMemo = memoSummarizer.analyze(request.getSttMemo());
    return ResponseEntity.ok().body(new MemoSummaryResponse(summaryMemo));
  }


}
