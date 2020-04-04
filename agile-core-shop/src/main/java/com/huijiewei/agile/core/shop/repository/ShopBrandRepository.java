package com.huijiewei.agile.core.shop.repository;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.huijiewei.agile.core.repository.ValidSaveRepository;
import com.huijiewei.agile.core.shop.entity.ShopBrand;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ShopBrandRepository extends EntityGraphJpaRepository<ShopBrand, Integer>, EntityGraphJpaSpecificationExecutor<ShopBrand>, ValidSaveRepository<ShopBrand> {
    List<ShopBrand> findAll(@Nullable Specification<ShopBrand> spec, @Nullable Sort sort, EntityGraph entityGraph);
}
