package motgolla.infra.openai;

import motgolla.domain.record.service.MemoSummarizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoSummarizerProductImpressionTest {

  @Autowired
  private MemoSummarizer memoSummarizer;

  @Test
  @DisplayName("상품명과 브랜드가 포함된 STT 메모를 정리한다")
  void summarizeBrandAndProductName() {
    String sttMemo = "음… 이거는 어… 나이키에서 나온 에어맥스 90이고요, 색상은 화이트에요.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 상품명 정리: " + result);

    assertThat(result).contains("나이키", "에어맥스").doesNotContain("음…").doesNotContain("어…");
  }

  @Test
  @DisplayName("구매 시점과 장소를 자연스럽게 정리한다")
  void summarizePurchaseDateAndPlace() {
    String sttMemo = "으음… 지난주 토요일에 홍대 나이키 매장에서 샀어요. 음… 사람이 많았어요.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 구매 정보 정리: " + result);

    assertThat(result).contains("홍대", "토요일").doesNotContain("으음…").doesNotContain("음…");
  }

  @Test
  @DisplayName("착용 후기를 정돈된 문장으로 정리한다")
  void summarizeWearingFeedback() {
    String sttMemo = "아… 이거 신었을 때 착화감이 엄청 좋고, 음… 하루 종일 걸어도 발이 안 아플거같아요. 완전 짱이네";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 착용감 정리: " + result);

    assertThat(result).doesNotContain("아…").doesNotContain("음…");
  }
  @Test
  @DisplayName("아버지 선물로 고민 중인 상품 기록을 자연스럽게 정리한다")
  void summarizeGiftConsiderationMemo() {
    String sttMemo = "음… 아버지 선물로 셔츠를 살까 하는데, 흰색 좋아하셔서… 그래서 지금 상품 기록하려는 타미힐피거가 무난하고 나은 것 같고요. 어.. 타마힐피거 가격대가 어… 10만원 초반 정도라 나쁘지 않네요.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🎁 선물 후보 상품 고민 정리: " + result);

    assertThat(result).doesNotContain("음…").doesNotContain("그니까").doesNotContain("어…");
  }

  @Test
  @DisplayName("가격 정보가 포함된 메모를 정리한다")
  void summarizePriceInfo() {
    String sttMemo = "이거는 어… 세일해서 89,000원에 샀고요, 원래 가격은 119,000원이었어요.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 가격 정보 정리: " + result);

    assertThat(result).contains("89,000", "119,000").doesNotContain("어…");
  }

  @Test
  @DisplayName("상품 인상을 표현한 감탄사 위주 메모를 자연스럽게 정리한다")
  void summarizeImpressionMemo() {
    String sttMemo = "아 이거 비싸보이긴 한데 좋아보이네. 음… 이쁘다, 색깔 진짜 맘에 들어.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 인상 정리: " + result);

    assertThat(result).doesNotContain("음…");
  }

  @Test
  @DisplayName("감탄사나 반복 표현이 많은 메모도 핵심만 정리한다")
  void summarizeFillerHeavyMemo() {
    String sttMemo = "오 이거 진짜 대박이다, 어… 디자인도 진짜 예쁘고… 음… 완전 내 스타일이야.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 감탄사 정리: " + result);

    assertThat(result).doesNotContain("어…").doesNotContain("음…");
  }

  @Test
  @DisplayName("매우 짧은 감정 표현도 자연스럽게 정리한다")
  void summarizeShortFeelingMemo() {
    String sttMemo = "음… 이쁘다.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 짧은 느낌 정리: " + result);

    assertThat(result).doesNotContain("음…");
  }

  @Test
  @DisplayName("복잡하고 중복된 설명이 많은 메모도 간결하게 정리한다")
  void summarizeComplexMemo() {
    String sttMemo = "어… 이거는 그러니까… 으음… 나이키 매장에서 샀고요, 그니까… 음… 디자인도 예쁘고, 착용감도 좋고요, 진짜 예뻐요.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 복잡한 메모 정리: " + result);

    assertThat(result).doesNotContain("어…").doesNotContain("그러니까…").doesNotContain("으음…").doesNotContain("음…");
  }
  @Test
  @DisplayName("비속어가 포함된 상품 메모를 순화하여 정리한다")
  void summarizeMemoWithProfanity() {
    String sttMemo = "아 이거 개좋아 보인다. 진짜 예쁘다.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 비속어 포함 메모 정리: " + result);

    assertThat(result).doesNotContain("개좋아");
  }

  @Test
  @DisplayName("심한 욕설이 섞인 STT 메모를 순화하여 정리한다")
  void summarizeMemoWithHarshProfanity() {
    String sttMemo = "이거 씨발 비싸네. ㅈ같이 생겼네.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 강한 비속어 정리: " + result);

    assertThat(result).doesNotContain("씨").doesNotContain("ㅈ같");
  }

  @Test
  @DisplayName("약한 비속어 표현도 순화해 정리한다")
  void summarizeMemoWithLightProfanity() {
    String sttMemo = "디자인은 존나 괜찮고, 색깔도 맘에 드네.";
    String result = memoSummarizer.analyze(sttMemo);
    System.out.println("🧾 경미한 비속어 정리: " + result);

    assertThat(result).doesNotContain("존");
  }

}
