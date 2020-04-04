package com.huijiewei.agile.core.shop.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.huijiewei.agile.core.exception.ConflictException;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.response.SearchListResponse;
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
import com.huijiewei.agile.core.shop.response.ShopBrandBaseResponse;
import com.huijiewei.agile.core.shop.response.ShopBrandResponse;
import com.huijiewei.agile.core.shop.response.ShopCategoryBaseResponse;
import org.springframework.data.domain.Sort;
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

    public SearchListResponse<ShopBrandResponse> getAll(ShopBrandSearchRequest request) {
        SearchListResponse<ShopBrandResponse> response = new SearchListResponse<>();
        response.setSearchFields(request.getSearchFields());

        List<ShopBrandResponse> shopBrandResponses = ShopBrandMapper.INSTANCE.toShopBrandResponses(this.shopBrandRepository.findAll(request.getSpecification(), Sort.by(Sort.Direction.ASC, "id"), EntityGraphUtils.fromAttributePaths("shopCategories")));

        for (int i = 0; i < shopBrandResponses.size(); i++) {
            ShopBrandResponse shopBrandResponse = shopBrandResponses.get(i);
            shopBrandResponses.set(i, this.fillCategories(shopBrandResponse));
        }

        response.setItems(shopBrandResponses);

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
        if (shopBrandResponse.getShopCategories() != null) {
            List<ShopCategoryBaseResponse> shopCategoryBaseResponses = shopBrandResponse.getShopCategories();

            for (int j = 0; j < shopCategoryBaseResponses.size(); j++) {
                ShopCategoryBaseResponse shopCategoryBaseResponse = shopCategoryBaseResponses.get(j);
                shopCategoryBaseResponse.setParents(ShopCategoryMapper.INSTANCE.toShopCategoryBaseResponses(this.shopCategoryManager.getParents(shopCategoryBaseResponse.getId())));
                shopCategoryBaseResponses.set(j, shopCategoryBaseResponse);
            }

            shopBrandResponse.setShopCategories(shopCategoryBaseResponses);
        }

        return shopBrandResponse;
    }

    public ShopBrandResponse edit(Integer id, @Valid ShopBrandRequest request) {
        Optional<ShopBrand> shopBrandOptional = this.shopBrandRepository.findById(id);

        if (shopBrandOptional.isEmpty()) {
            throw new NotFoundException("商品品牌不存在");
        }

        ShopBrand current = shopBrandOptional.get();

        ShopBrand shopBrand = ShopBrandMapper.INSTANCE.toShopBrand(request, current);

        this.shopBrandRepository.saveWithValid(shopBrand);

        this.shopBrandCategoryRepository.deleteAllByShopBrandId(id);

        this.updateShopCategories(shopBrand.getId(), request.getShopCategoryIds());

        return this.fillCategories(ShopBrandMapper.INSTANCE.toShopBrandResponse(shopBrand));
    }

    public void delete(Integer id) {
        if (!this.shopBrandRepository.existsById(id)) {
            throw new NotFoundException("商品品牌不存在");
        }

        if (this.shopProductRepository.existsByShopBrandId(id)) {
            throw new ConflictException("商品品牌内存在商品，无法删除");
        }

        this.shopBrandCategoryRepository.deleteAllByShopBrandId(id);

        this.shopBrandRepository.deleteById(id);
    }
}
