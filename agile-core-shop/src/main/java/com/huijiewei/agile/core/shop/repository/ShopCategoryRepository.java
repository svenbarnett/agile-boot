package com.huijiewei.agile.core.shop.repository;

import com.huijiewei.agile.core.shop.entity.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Integer> {

}
