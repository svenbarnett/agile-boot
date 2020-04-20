package com.huijiewei.agile.core.shop.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.core.exception.ConflictException;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.response.SearchPageResponse;
import com.huijiewei.agile.core.shop.entity.ShopBrand;
import com.huijiewei.agile.core.shop.entity.ShopBrandCategory;
import com.huijiewei.agile.core.shop.manager.ShopCategoryManager;
import com.huijiewei.agile.core.shop.mapper.ShopBrandMapper;
import com.huijiewei.agile.core.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.core.shop.repository.ShopBrandCategoryRepository;
import com.huijiewei.agile.core.shop.repository.ShopBrandRepository;
import com.huijiewei.agile.core.shop.repository.ShopProductRepository;
import com.huijiewei.agile.core.shop.request.ShopBrandRequest;
import com.huijiewei.agile.core.shop.request.ShopBrandSearchRequest;
import com.huijiewei.agile.core.shop.response.ShopBrandResponse;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ShopBrandService {
    private final ShopBrandRepository shopBrandRepository;
    private final ShopProductRepository shopProductRepository;
    private final ShopCategoryManager shopCategoryManager;
    private final ShopBrandCategoryRepository shopBrandCategoryRepository;

    public ShopBrandService(ShopBrandRepository shopBrandRepository, ShopProductRepository shopProductRepository, ShopCategoryManager shopCategoryManager, ShopBrandCategoryRepository shopBrandCategoryRepository) {
        this.shopBrandRepository = shopBrandRepository;
        this.shopProductRepository = shopProductRepository;
        this.shopCategoryManager = shopCategoryManager;
        this.shopBrandCategoryRepository = shopBrandCategoryRepository;
    }

    public SearchPageResponse<ShopBrandResponse> search(Boolean withSearchFields, ShopBrandSearchRequest request, Pageable pageable) {
        Page<ShopBrandResponse> shopBrands = ShopBrandMapper.INSTANCE.toShopBrandResponses(
                this.shopBrandRepository.findAll(
                        request.getSpecification(),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sorts.builder().desc("id").build()),
                        EntityGraphUtils.fromAttributePaths("shopCategories")
                )
        );

        SearchPageResponse<ShopBrandResponse> response = new SearchPageResponse<>();
        response.setPage(shopBrands);

        if (withSearchFields != null && withSearchFields) {
            response.setSearchFields(request.getSearchFields());
        }

        return response;
    }

    public ShopBrandResponse view(Integer id) {
        ShopBrand shopBrand = this.getById(id);

        return this.fillCategories(ShopBrandMapper.INSTANCE.toShopBrandResponse(shopBrand));
    }

    private ShopBrand getById(Integer id) {
        Optional<ShopBrand> shopBrandOptional = this.shopBrandRepository.findById(id);

        if (shopBrandOptional.isEmpty()) {
            throw new NotFoundException("商品品牌不存在");
        }

        return shopBrandOptional.get();
    }

    public ShopBrandResponse create(@Valid ShopBrandRequest request) {
        ShopBrand shopBrand = ShopBrandMapper.INSTANCE.toShopBrand(request);

        this.shopBrandRepository.saveWithValid(shopBrand);

        this.updateShopCategories(shopBrand.getId(), request.getShopCategoryIds());

        return this.fillCategories(ShopBrandMapper.INSTANCE.toShopBrandResponse(shopBrand));
    }

    private void updateShopCategories(Integer shopBrandId, List<Integer> shopCategoryIds) {
        List<ShopBrandCategory> shopBrandCategories = new ArrayList<>();

        for (Integer shopCategoryId : shopCategoryIds) {
            ShopBrandCategory shopBrandCategory = new ShopBrandCategory();
            shopBrandCategory.setShopBrandId(shopBrandId);
            shopBrandCategory.setShopCategoryId(shopCategoryId);
            shopBrandCategories.add(shopBrandCategory);
        }

        this.shopBrandCategoryRepository.batchInsert(shopBrandCategories);
    }

    private ShopBrandResponse fillCategories(ShopBrandResponse shopBrandResponse) {
        List<ShopCategoryResponse> shopCategoryResponses = shopBrandResponse.getShopCategories();

        if (shopCategoryResponses != null) {
            for (int i = 0; i < shopCategoryResponses.size(); i++) {
                ShopCategoryResponse shopCategoryResponse = shopCategoryResponses.get(i);
                shopCategoryResponse.setParents(ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.shopCategoryManager.getParents(shopCategoryResponse.getParentId())));
                shopCategoryResponses.set(i, shopCategoryResponse);
            }

            shopBrandResponse.setShopCategories(shopCategoryResponses);
        }

        return shopBrandResponse;
    }

    public ShopBrandResponse edit(Integer id, @Valid ShopBrandRequest request) {
        ShopBrand current = this.getById(id);

        ShopBrand shopBrand = ShopBrandMapper.INSTANCE.toShopBrand(request, current);

        this.shopBrandRepository.saveWithValid(shopBrand);

        this.shopBrandCategoryRepository.deleteAllByShopBrandId(id);

        this.updateShopCategories(shopBrand.getId(), request.getShopCategoryIds());

        return this.fillCategories(ShopBrandMapper.INSTANCE.toShopBrandResponse(shopBrand));
    }

    public void delete(Integer id) {
        ShopBrand shopBrand = this.getById(id);

        if (this.shopProductRepository.existsByShopBrandId(id)) {
            throw new ConflictException("商品品牌内存在商品，无法删除");
        }

        this.shopBrandCategoryRepository.deleteAllByShopBrandId(id);

        this.shopBrandRepository.delete(shopBrand);
    }
}
