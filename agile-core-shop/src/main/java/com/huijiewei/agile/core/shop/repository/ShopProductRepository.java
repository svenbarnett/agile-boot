package com.huijiewei.agile.core.shop.repository;

import com.huijiewei.agile.core.shop.entity.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer>, JpaSpecificationExecutor<ShopProduct> {
    Boolean existsByShopCategoryIdIn(List<Integer> shopCategoryId);

    Boolean existsByShopBrandId(Integer shopBrandId);
}
