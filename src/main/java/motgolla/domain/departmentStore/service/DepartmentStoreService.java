package motgolla.domain.departmentStore.service;

import motgolla.domain.departmentStore.dto.response.DepartmentStoreIdResponse;
import motgolla.domain.departmentStore.dto.response.DepartmentStoreResponse;

import java.util.List;

public interface DepartmentStoreService {
	DepartmentStoreResponse findNearestStore(double lat, double lon);

	List<DepartmentStoreIdResponse> getAllStores();
}
