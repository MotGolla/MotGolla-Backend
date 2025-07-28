package motgolla.domain.product.mapper;

import motgolla.domain.product.vo.Product;
import motgolla.domain.recommend.dto.response.RecommendedProduct;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAllForRecommend();
    boolean existsById(@Param("id") Long id);
    List<RecommendedProduct> findRecommendationsByProductId(@Param("productId") Long productId);
}
