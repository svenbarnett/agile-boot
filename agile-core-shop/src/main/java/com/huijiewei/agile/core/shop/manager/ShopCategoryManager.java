package com.huijiewei.agile.core.shop.manager;

import com.huijiewei.agile.core.manager.TreeManager;
import com.huijiewei.agile.core.shop.entity.ShopCategory;
import com.huijiewei.agile.core.shop.repository.ShopCategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopCategoryManager extends TreeManager<ShopCategory> {
    private static final String SHOP_CATEGORIES_CACHE_KEY = "shop-categories";
    private static final String SHOP_CATEGORY_TREE_CACHE_KEY = "shop-category-tree";

    private final ShopCategoryRepository shopCategoryRepository;

    public ShopCategoryManager(ShopCategoryRepository shopCategoryRepository) {
        this.shopCategoryRepository = shopCategoryRepository;
    }

    @Cacheable(value = SHOP_CATEGORIES_CACHE_KEY)
    public List<ShopCategory> getAll() {
        return this.shopCategoryRepository.findAll();
    }

    @Cacheable(value = SHOP_CATEGORY_TREE_CACHE_KEY)
    public List<ShopCategory> getTree() {
        return super.getTree();
    }

    @CacheEvict(value = {SHOP_CATEGORIES_CACHE_KEY, SHOP_CATEGORY_TREE_CACHE_KEY}, allEntries = true)
    public void save(ShopCategory shopCategory) {
        this.shopCategoryRepository.save(shopCategory);
    }

    @CacheEvict(value = {SHOP_CATEGORIES_CACHE_KEY, SHOP_CATEGORY_TREE_CACHE_KEY}, allEntries = true)
    public void delete(List<Integer> ids) {
        this.shopCategoryRepository.deleteAllByIds(ids);
    }
}
