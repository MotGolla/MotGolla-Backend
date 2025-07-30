package motgolla.domain.vote.service;

import lombok.RequiredArgsConstructor;
import motgolla.domain.vote.dto.VoteDetailDto;
import motgolla.domain.vote.dto.request.VoteCreateRequest;
import motgolla.domain.vote.dto.response.VoteDetailResponse;
import motgolla.domain.vote.mapper.VoteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteMapper voteMapper;

    @Override
    public Long createVote(Long memberId, VoteCreateRequest request) {
        voteMapper.insertVoteGroup(request.getTitle(), memberId);
        Long voteGroupId = voteMapper.getLastInsertedVoteGroupId();
        for (Long recordId : request.getRecordIds()) {
            voteMapper.insertVoteCandidate(voteGroupId, recordId, memberId);
        }

        return voteGroupId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoteDetailResponse> getVotes(Long memberId, String type) {
        List<VoteDetailResponse> response = voteMapper.findVoteDetails(memberId, type);
        for (VoteDetailResponse vote : response) {
            String formatted = calculateTimeAgo(vote.getCreatedAt());
            vote.setTimeAgo(formatted);
        }

        return response;
    }

    @Override
    public void vote(Long memberId, Long voteGroupId, Long voteCandidateId) {
        voteMapper.insertVote(memberId, voteGroupId, voteCandidateId);
    }

    private String calculateTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 1) {
            return "방금 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else if (days < 7) {
            return days + "일 전";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return createdAt.format(formatter);
        }
    }
}

