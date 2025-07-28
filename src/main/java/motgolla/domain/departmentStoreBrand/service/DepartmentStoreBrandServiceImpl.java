package motgolla.domain.departmentStoreBrand.service;

import lombok.RequiredArgsConstructor;
import motgolla.domain.departmentStoreBrand.mapper.DepartmentStoreBrandMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentStoreBrandServiceImpl implements DepartmentStoreBrandService{
    private final DepartmentStoreBrandMapper departmentStoreBrandMapper;

    @Override
    public List<String> getBrandLocations(Long departmentStoreId, String brandName) {
        return departmentStoreBrandMapper.findBrandLocations(departmentStoreId, brandName);
    }
}
