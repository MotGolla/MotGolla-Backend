package motgolla.domain.departmentStore.service;

import motgolla.domain.departmentStore.dto.response.DepartmentStoreResponse;

public interface DepartmentStoreService {
	DepartmentStoreResponse findNearestStore(double lat, double lon);
}
