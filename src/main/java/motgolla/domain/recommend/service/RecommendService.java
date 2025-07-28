package motgolla.domain.recommend.service;

import java.util.List;

import motgolla.domain.recommend.dto.response.RecommendedProduct;

public interface RecommendService {
	List<RecommendedProduct> getRecommendedProducts(Long productId);
}
