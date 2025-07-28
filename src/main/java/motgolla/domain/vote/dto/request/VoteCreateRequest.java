package motgolla.domain.vote.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class VoteCreateRequest {
    private String title;
    private List<Long> recordIds;
}
