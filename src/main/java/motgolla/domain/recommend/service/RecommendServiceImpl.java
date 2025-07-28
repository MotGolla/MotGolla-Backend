package motgolla.domain.recommend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.product.mapper.ProductMapper;
import motgolla.domain.recommend.dto.response.RecommendedProduct;
import motgolla.domain.recommend.mapper.RecommendMapper;
import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;
import motgolla.global.error.exception.EntityNotFoundException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService{

	private final RecommendMapper recommendMapper;
	private final ProductMapper productMapper;

	@Override
	public List<RecommendedProduct> getRecommendedProducts(Long productId) {
		if(!productMapper.existsById(productId)){
			throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
		}

		return recommendMapper.findRecommendationsById(productId);
	}
}
