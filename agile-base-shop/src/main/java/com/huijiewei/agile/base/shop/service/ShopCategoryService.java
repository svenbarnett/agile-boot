package com.huijiewei.agile.base.shop.service;

import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.service.TreeService;
import com.huijiewei.agile.base.shop.entity.ShopCategory;
import com.huijiewei.agile.base.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.base.shop.repository.ShopCategoryRepository;
import com.huijiewei.agile.base.shop.request.ShopCategoryRequest;
import com.huijiewei.agile.base.shop.response.ShopCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ShopCategoryService extends TreeService<ShopCategory> {
    private final ShopCategoryRepository shopCategoryRepository;

    @Autowired
    public ShopCategoryService(ShopCategoryRepository shopCategoryRepository) {
        this.shopCategoryRepository = shopCategoryRepository;
    }

    @Cacheable(value = "shop-categories")
    public List<ShopCategory> findAll() {
        return this.shopCategoryRepository.findAll();
    }

    private List<ShopCategoryResponse> getParentsById(Integer id) {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.buildParents(id, this.findAll()));
    }

    @Cacheable(value = "shop-category-tree")
    public List<ShopCategoryResponse> getTree() {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.buildTree(this.findAll()));
    }

    public List<ShopCategoryResponse> getRoute(Integer id) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("分类不存在");
        }

        return this.getParentsById(id);
    }

    public ShopCategoryResponse getById(Integer id, Boolean withParents) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("分类不存在");
        }

        ShopCategory shopCategory = shopCategoryOptional.get();

        ShopCategoryResponse response = ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);

        if (withParents) {
            response.setParents(this.getParentsById(shopCategory.getParentId()));
        }

        return response;
    }

    @CacheEvict(value = {"shop-categories", "shop-category-tree"}, allEntries = true)
    public ShopCategoryResponse create(@Valid ShopCategoryRequest request) {
        ShopCategory shopCategory = ShopCategoryMapper.INSTANCE.toShopCategory(request);

        if (shopCategory.getParentId() > 0 && !this.shopCategoryRepository.existsById(shopCategory.getParentId())) {
            throw new NotFoundException("你选择的上级分类不存在");
        }

        this.shopCategoryRepository.save(shopCategory);

        return ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);
    }

    @CacheEvict(value = {"shop-categories", "shop-category-tree"}, allEntries = true)
    public ShopCategoryResponse edit(Integer id, @Valid ShopCategoryRequest request) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("商品分类不存在");
        }

        ShopCategory current = shopCategoryOptional.get();

        ShopCategory shopCategory = ShopCategoryMapper.INSTANCE.toShopCategory(request, current);

        if (shopCategory.getParentId() > 0 && !this.shopCategoryRepository.existsById(shopCategory.getParentId())) {
            throw new NotFoundException("你选择的上级分类不存在");
        }

        this.shopCategoryRepository.save(shopCategory);

        return ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);
    }
}
