package com.huijiewei.agile.base.shop.service;

import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.service.TreeService;
import com.huijiewei.agile.base.shop.entity.ShopCategory;
import com.huijiewei.agile.base.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.base.shop.repository.ShopCategoryRepository;
import com.huijiewei.agile.base.shop.response.ShopCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    @Cacheable(value = "shop-category-parents", key = "#id")
    public List<ShopCategoryResponse> getParentsById(Integer id) {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.buildParents(id, this.findAll()));
    }

    @Cacheable(value = "shop-category-tree")
    public List<ShopCategoryResponse> getTree() {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.buildTree(this.findAll()));
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
}
