package motgolla.infra.openai.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class OpenAiResponse {
  private List<Choice> choices;

  @Getter
  public static class Choice {
    private Message message;
  }

  @Getter
  public static class Message {
    private String content;
  }
}
