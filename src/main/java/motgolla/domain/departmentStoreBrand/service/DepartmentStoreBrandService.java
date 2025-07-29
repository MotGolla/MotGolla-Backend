package motgolla.domain.departmentStoreBrand.service;

import motgolla.domain.departmentStoreBrand.dto.response.BrandLocationResponse;

import java.util.List;

public interface DepartmentStoreBrandService {
    List<BrandLocationResponse> getBrandLocations(Long departmentStoreId, String brandName);
}
