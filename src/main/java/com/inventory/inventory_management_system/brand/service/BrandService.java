package com.inventory.inventory_management_system.brand.service;

import com.inventory.inventory_management_system.brand.dto.request.CreateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.request.UpdateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.response.BrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface BrandService {
    BrandResponse createBrand(@NonNull CreateBrandRequest request);
    BrandResponse getBrandById(@NonNull Long id);
    Page<BrandResponse> searchBrands(String name, Boolean active, @NonNull Pageable pageable);
    BrandResponse updateBrand(@NonNull Long id, @NonNull UpdateBrandRequest request);
    void deleteBrand(@NonNull Long id);
}
