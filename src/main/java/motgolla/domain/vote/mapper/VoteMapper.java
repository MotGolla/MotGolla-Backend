package motgolla.domain.vote.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteMapper {
    void insertVoteGroup(@Param("title") String title, @Param("memberId") Long memberId);

    Long getLastInsertedVoteGroupId();

    void insertVoteCandidate(@Param("voteGroupId") Long voteGroupId,
                              @Param("recordId") Long recordId,
                              @Param("memberId") Long memberId);
}
