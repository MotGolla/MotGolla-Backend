package motgolla.domain.product.service;

import java.util.List;

import motgolla.domain.recommend.dto.response.RecommendedProduct;

public interface ProductService {
	List<RecommendedProduct> findRecommendationsByProductId(Long productId);
}
