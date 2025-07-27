package motgolla.domain.vote.service;

import motgolla.domain.vote.dto.request.VoteCreateRequest;
import org.springframework.stereotype.Service;

public interface VoteService {
    Long createVote(Long id, VoteCreateRequest request);
}
