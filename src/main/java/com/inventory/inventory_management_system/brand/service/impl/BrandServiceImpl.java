package com.inventory.inventory_management_system.brand.service.impl;

import com.inventory.inventory_management_system.brand.dto.request.CreateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.request.UpdateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.response.BrandResponse;
import com.inventory.inventory_management_system.brand.entity.Brand;
import com.inventory.inventory_management_system.brand.mapper.BrandMapper;
import com.inventory.inventory_management_system.brand.repository.BrandRepository;
import com.inventory.inventory_management_system.brand.service.BrandService;
import com.inventory.inventory_management_system.brand.specification.BrandSpecification;
import com.inventory.inventory_management_system.brand.validator.BrandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final BrandValidator brandValidator;

    @Override
    public BrandResponse createBrand(@NonNull CreateBrandRequest request) {
        brandValidator.validateCreate(request);
        Brand brand = brandMapper.toEntity(request);
        brand.setActive(true);
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandById(@NonNull Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        return brandMapper.toResponse(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandResponse> searchBrands(String name, Boolean active, @NonNull Pageable pageable) {
        Specification<Brand> spec = Specification.where(BrandSpecification.hasNameLike(name))
                .and(BrandSpecification.isActive(active));
        return brandRepository.findAll(spec, pageable).map(brandMapper::toResponse);
    }

    @Override
    public BrandResponse updateBrand(@NonNull Long id, @NonNull UpdateBrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        brandMapper.updateEntityFromDto(request, brand);
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(@NonNull Long id) {
        if (!brandRepository.existsById(id)) {
            throw new RuntimeException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
}
