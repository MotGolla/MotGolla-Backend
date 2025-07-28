package motgolla.domain.departmentStoreBrand.service;

import java.util.List;

public interface DepartmentStoreBrandService {
    List<String> getBrandLocations(Long departmentStoreId, String brandName);
}
