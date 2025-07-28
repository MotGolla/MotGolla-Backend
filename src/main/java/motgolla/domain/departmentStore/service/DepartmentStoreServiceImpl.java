package motgolla.domain.departmentStore.service;

import lombok.RequiredArgsConstructor;
import motgolla.domain.departmentStore.dto.response.DepartmentStoreIdResponse;
import motgolla.domain.departmentStore.dto.response.DepartmentStoreResponse;
import motgolla.domain.departmentStore.mapper.DepartmentStoreMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentStoreServiceImpl implements DepartmentStoreService {

    private final DepartmentStoreMapper departmentStoreMapper;

    @Override
    public DepartmentStoreResponse findNearestStore(double lat, double lon) {
        return departmentStoreMapper.findNearestStore(lat, lon);
    }

    @Override
    public List<DepartmentStoreIdResponse> getAllStores() {
        return departmentStoreMapper.findAllStores();
    }
}
