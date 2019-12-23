package com.huijiewei.agile.core.shop.service;

import com.huijiewei.agile.core.exception.ConflictException;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.response.SearchListResponse;
import com.huijiewei.agile.core.shop.entity.ShopBrand;
import com.huijiewei.agile.core.shop.mapper.ShopBrandMapper;
import com.huijiewei.agile.core.shop.repository.ShopBrandRepository;
import com.huijiewei.agile.core.shop.repository.ShopProductRepository;
import com.huijiewei.agile.core.shop.request.ShopBrandRequest;
import com.huijiewei.agile.core.shop.request.ShopBrandSearchRequest;
import com.huijiewei.agile.core.shop.response.ShopBrandBaseResponse;
import com.huijiewei.agile.core.shop.response.ShopBrandResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ShopBrandService {
    private final ShopBrandRepository shopBrandRepository;
    private final ShopProductRepository shopProductRepository;

    public ShopBrandService(ShopBrandRepository shopBrandRepository, ShopProductRepository shopProductRepository) {
        this.shopBrandRepository = shopBrandRepository;
        this.shopProductRepository = shopProductRepository;
    }

    public SearchListResponse<ShopBrandResponse> getAll(ShopBrandSearchRequest request) {
        SearchListResponse<ShopBrandResponse> response = new SearchListResponse<>();
        response.setSearchFields(request.getSearchFields());
        response.setItems(ShopBrandMapper.INSTANCE.toShopBrandResponses(this.shopBrandRepository.findAll(request.getSpecification())));

        return response;
    }

    public List<ShopBrandBaseResponse> getList() {
        return ShopBrandMapper.INSTANCE.toShopBrandBaseResponses(this.shopBrandRepository.findAll());
    }

    public ShopBrandResponse getById(Integer id) {
        Optional<ShopBrand> shopBrandOptional = this.shopBrandRepository.findById(id);

        if (shopBrandOptional.isEmpty()) {
            throw new NotFoundException("商品品牌不存在");
        }

        return ShopBrandMapper.INSTANCE.toShopBrandResponse(shopBrandOptional.get());
    }

    public ShopBrandResponse create(@Valid ShopBrandRequest request) {
        ShopBrand shopBrand = ShopBrandMapper.INSTANCE.toShopBrand(request);

        this.shopBrandRepository.save(shopBrand);

        return ShopBrandMapper.INSTANCE.toShopBrandResponse(shopBrand);
    }

    public ShopBrandResponse edit(Integer id, @Valid ShopBrandRequest request) {
        Optional<ShopBrand> shopBrandOptional = this.shopBrandRepository.findById(id);

        if (shopBrandOptional.isEmpty()) {
            throw new NotFoundException("商品品牌不存在");
        }

        ShopBrand current = shopBrandOptional.get();

        ShopBrand shopBrand = ShopBrandMapper.INSTANCE.toShopBrand(request, current);

        this.shopBrandRepository.save(shopBrand);

        return ShopBrandMapper.INSTANCE.toShopBrandResponse(shopBrand);
    }

    public void delete(Integer id) {
        if (!this.shopBrandRepository.existsById(id)) {
            throw new NotFoundException("商品品牌不存在");
        }

        if (this.shopProductRepository.existsByShopBrandId(id)) {
            throw new ConflictException("商品品牌内存在商品，无法删除");
        }

        this.shopBrandRepository.deleteById(id);
    }
}
