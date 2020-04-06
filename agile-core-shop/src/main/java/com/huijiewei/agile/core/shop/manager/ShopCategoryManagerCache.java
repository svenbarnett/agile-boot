package com.huijiewei.agile.core.shop.manager;

import com.huijiewei.agile.core.manager.TreeManagerCache;
import com.huijiewei.agile.core.shop.entity.ShopCategory;
import com.huijiewei.agile.core.shop.repository.ShopCategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopCategoryManagerCache extends TreeManagerCache<ShopCategory> {
    public static final String SHOP_CATEGORIES_CACHE_KEY = "shop-categories";

    private final ShopCategoryRepository shopCategoryRepository;

    public ShopCategoryManagerCache(ShopCategoryRepository shopCategoryRepository) {
        this.shopCategoryRepository = shopCategoryRepository;
    }

    @Cacheable(cacheNames = SHOP_CATEGORIES_CACHE_KEY)
    public List<ShopCategory> getAll() {
        return this.shopCategoryRepository.findAll();
    }
}
