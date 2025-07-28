package motgolla.domain.vote.service;

import motgolla.domain.vote.dto.request.VoteCreateRequest;
import motgolla.domain.vote.dto.response.VoteDetailResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VoteService {
    Long createVote(Long id, VoteCreateRequest request);

    List<VoteDetailResponse> getVotes(Long memberId, String type);

    void vote(Long memberId, Long voteGroupId, Long voteCandidateId);
}
