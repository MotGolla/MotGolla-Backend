package motgolla.domain.recommend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecommendMapper {
    void saveRecommendations(@Param("productId") Long productId, @Param("recommendedIds") List<String> recommendedIds);
}
