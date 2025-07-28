package motgolla.domain.vote.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDetailDto {
    private Long voteGroupId;
    private String voteTitle;
    private LocalDateTime createdAt;
    private String profileImage;
    private String nickname;
    private boolean isMine;
    private boolean isVotedByMe;
    private Long candidateId;
    private Long recordId;
    private int voteCount;
    private int totalVotes;
    private String imageUrl;
}
