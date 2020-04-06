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
    private static final String SHOP_CATEGORY_TREE_CACHE_KEY = "shop-category-tree";

    private final ShopCategoryRepository shopCategoryRepository;
    private final ShopCategoryManagerCache shopCategoryManagerCache;

    public ShopCategoryManager(ShopCategoryRepository shopCategoryRepository, ShopCategoryManagerCache shopCategoryManagerCache) {
        this.shopCategoryRepository = shopCategoryRepository;
        this.shopCategoryManagerCache = shopCategoryManagerCache;
    }

    public List<ShopCategory> getAll() {
        return this.shopCategoryManagerCache.getAll();
    }

    @Cacheable(cacheNames = SHOP_CATEGORY_TREE_CACHE_KEY)
    public List<ShopCategory> getTree() {
        return super.getTree();
    }

    @CacheEvict(cacheNames = {ShopCategoryManagerCache.SHOP_CATEGORIES_CACHE_KEY, SHOP_CATEGORY_TREE_CACHE_KEY}, allEntries = true)
    public void save(ShopCategory shopCategory) {
        this.shopCategoryRepository.save(shopCategory);
    }

    @CacheEvict(cacheNames = {ShopCategoryManagerCache.SHOP_CATEGORIES_CACHE_KEY, SHOP_CATEGORY_TREE_CACHE_KEY}, allEntries = true)
    public void delete(List<Integer> ids) {
        this.shopCategoryRepository.deleteAllByIds(ids);
    }
}
