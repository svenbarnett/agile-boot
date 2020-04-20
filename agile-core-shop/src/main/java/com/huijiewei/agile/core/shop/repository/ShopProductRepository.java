package com.huijiewei.agile.core.shop.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.huijiewei.agile.core.shop.entity.ShopProduct;

import java.util.List;

public interface ShopProductRepository extends
        EntityGraphJpaRepository<ShopProduct, Integer>,
        EntityGraphJpaSpecificationExecutor<ShopProduct> {
    Boolean existsByShopCategoryIdIn(List<Integer> shopCategoryId);

    Boolean existsByShopBrandId(Integer shopBrandId);
}
