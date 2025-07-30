package motgolla.domain.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import motgolla.domain.departmentStore.mapper.DepartmentStoreMapper;
import motgolla.domain.product.mapper.ProductMapper;
import motgolla.domain.recommend.dto.response.RecommendedProduct;
import motgolla.global.error.ErrorCode;
import motgolla.global.error.exception.BusinessException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductMapper productMapper;
	private final DepartmentStoreMapper departmentStoreMapper;

	@Override
	public List<RecommendedProduct> findRecommendationsByProductId(Long productId, Long departmentStoreId) {
		if(!productMapper.existsById(productId)){
			throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
		}

		if(!departmentStoreMapper.existsStore(departmentStoreId)){
			throw new BusinessException(ErrorCode.DEPARTMENT_STORE_NOT_FOUND);
		}

		return productMapper.findRecommendationsByProductId(productId, departmentStoreId);
	}
}
