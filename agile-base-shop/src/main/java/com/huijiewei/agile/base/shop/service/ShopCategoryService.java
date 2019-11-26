package com.huijiewei.agile.base.shop.service;

import com.huijiewei.agile.base.service.TreeService;
import com.huijiewei.agile.base.shop.entity.ShopCategory;
import com.huijiewei.agile.base.shop.repository.ShopCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCategoryService extends TreeService<ShopCategory, ShopCategoryRepository> {
    private final ShopCategoryRepository shopCategoryRepository;

    @Autowired
    public ShopCategoryService(ShopCategoryRepository shopCategoryRepository) {
        this.shopCategoryRepository = shopCategoryRepository;
    }

    @Override
    protected ShopCategoryRepository repository() {
        return this.shopCategoryRepository;
    }
}
