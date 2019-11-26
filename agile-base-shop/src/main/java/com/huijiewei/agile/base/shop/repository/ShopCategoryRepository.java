package com.huijiewei.agile.base.shop.repository;

import com.huijiewei.agile.base.shop.entity.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Integer> {

}
