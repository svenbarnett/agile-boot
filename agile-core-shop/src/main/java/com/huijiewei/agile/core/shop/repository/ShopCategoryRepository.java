package com.huijiewei.agile.core.shop.repository;

import com.huijiewei.agile.core.shop.entity.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ShopCategory WHERE id IN ?1")
    public void deleteAllByIds(List<Integer> ids);
}
