package motgolla.domain.vote.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDetailResponse {
    private Long voteGroupId;
    private String title;
    private boolean isMine;
    private boolean isVotedByMe;
    private List<CandidateResult> candidates;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandidateResult {
        private Long candidateId;
        private Long recordId;
        private int voteCount;
        private int totalVotes;
        private Integer percentage;
        private String imageUrl;
    }
}