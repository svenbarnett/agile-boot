package com.huijiewei.agile.core.shop.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.huijiewei.agile.core.repository.ValidSaveRepository;
import com.huijiewei.agile.core.shop.entity.ShopBrand;

public interface ShopBrandRepository extends
        EntityGraphJpaRepository<ShopBrand, Integer>,
        EntityGraphJpaSpecificationExecutor<ShopBrand>,
        ValidSaveRepository<ShopBrand, Integer> {
}
