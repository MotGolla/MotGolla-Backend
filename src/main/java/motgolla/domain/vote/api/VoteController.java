package motgolla.domain.vote.api;

import lombok.RequiredArgsConstructor;
import motgolla.domain.member.vo.Member;
import motgolla.domain.vote.dto.request.VoteActionRequest;
import motgolla.domain.vote.dto.request.VoteCreateRequest;
import motgolla.domain.vote.dto.response.VoteCreateResponse;
import motgolla.domain.vote.dto.response.VoteDetailResponse;
import motgolla.domain.vote.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/votes")
@RestController
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteCreateResponse> createVote(
            @RequestBody VoteCreateRequest request,
            @AuthenticationPrincipal Member member
    ) {
        Long voteGroupId = voteService.createVote(member.getId(), request);
        return ResponseEntity.ok(new VoteCreateResponse(voteGroupId));
    }

    @GetMapping
    public List<VoteDetailResponse> getVoteList(@AuthenticationPrincipal Member member,
                                                @RequestParam(defaultValue = "all") String type) {
        return voteService.getVotes(member.getId(), type);
    }

    @PostMapping("/{voteGroupId}")
    public ResponseEntity<Void> vote(@PathVariable Long voteGroupId,
                                     @RequestBody VoteActionRequest request,
                                     @AuthenticationPrincipal Member member) {
        voteService.vote(member.getId(), voteGroupId, request.getVoteCandidateId());
        return ResponseEntity.ok().build();
    }

}
