package com.huijiewei.agile.core.shop.repository;

import com.huijiewei.agile.core.repository.ValidSaveRepository;
import com.huijiewei.agile.core.shop.entity.ShopBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopBrandRepository extends JpaRepository<ShopBrand, Integer>, JpaSpecificationExecutor<ShopBrand>, ValidSaveRepository<ShopBrand> {
}
