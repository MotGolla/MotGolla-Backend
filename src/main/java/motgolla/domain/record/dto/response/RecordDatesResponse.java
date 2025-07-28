package motgolla.domain.record.dto.response;

import java.util.List;

public class RecordDatesResponse {
  private List<String> dates;

  public RecordDatesResponse(List<String> dates) {
    this.dates = dates;
  }

  public List<String> getDates() {
    return dates;
  }

  public void setDates(List<String> dates) {
    this.dates = dates;
  }
}
