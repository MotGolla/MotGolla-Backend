package motgolla.domain.vote.api;

import lombok.RequiredArgsConstructor;
import motgolla.domain.member.vo.Member;
import motgolla.domain.vote.dto.request.VoteCreateRequest;
import motgolla.domain.vote.dto.response.VoteCreateResponse;
import motgolla.domain.vote.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
