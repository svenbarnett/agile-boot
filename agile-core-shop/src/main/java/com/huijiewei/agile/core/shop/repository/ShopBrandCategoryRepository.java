package com.huijiewei.agile.core.shop.repository;

import com.huijiewei.agile.core.repository.BatchRepository;
import com.huijiewei.agile.core.shop.entity.ShopBrandCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShopBrandCategoryRepository extends JpaRepository<ShopBrandCategory, Integer>, BatchRepository<ShopBrandCategory> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ShopBrandCategory WHERE shopBrandId = ?1")
    void deleteAllByShopBrandId(Integer id);
}
