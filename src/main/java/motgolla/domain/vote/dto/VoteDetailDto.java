package motgolla.domain.vote.dto;


import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDetailDto {
    private Long voteGroupId;
    private String voteTitle;
    private boolean isMine;
    private boolean isVotedByMe;
    private Long candidateId;
    private Long recordId;
    private int voteCount;
    private int totalVotes;
    private String imageUrl;
}
