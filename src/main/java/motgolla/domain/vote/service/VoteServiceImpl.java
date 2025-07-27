package motgolla.domain.vote.service;

import lombok.RequiredArgsConstructor;
import motgolla.domain.vote.dto.request.VoteCreateRequest;
import motgolla.domain.vote.mapper.VoteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteMapper voteMapper;

    @Transactional
    public Long createVote(Long memberId, VoteCreateRequest request) {
        voteMapper.insertVoteGroup(request.getTitle(), memberId);
        Long voteGroupId = voteMapper.getLastInsertedVoteGroupId();
        for (Long recordId : request.getRecordIds()) {
            voteMapper.insertVoteCandidate(voteGroupId, recordId, memberId);
        }

        return voteGroupId;
    }
}

