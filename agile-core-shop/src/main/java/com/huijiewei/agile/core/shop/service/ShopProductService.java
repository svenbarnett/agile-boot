package com.huijiewei.agile.core.shop.service;

import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.core.response.SearchPageResponse;
import com.huijiewei.agile.core.shop.entity.ShopProduct;
import com.huijiewei.agile.core.shop.manager.ShopCategoryManager;
import com.huijiewei.agile.core.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.core.shop.mapper.ShopProductMapper;
import com.huijiewei.agile.core.shop.repository.ShopProductRepository;
import com.huijiewei.agile.core.shop.request.ShopProductSearchRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryBaseResponse;
import com.huijiewei.agile.core.shop.response.ShopProductBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopProductService {
    private final ShopCategoryManager shopCategoryManager;
    private final ShopProductRepository shopProductRepository;

    @Autowired
    public ShopProductService(ShopCategoryManager shopCategoryManager, ShopProductRepository shopProductRepository) {
        this.shopCategoryManager = shopCategoryManager;
        this.shopProductRepository = shopProductRepository;
    }

    public SearchPageResponse<ShopProductBaseResponse> getAll(Boolean withSearchFields, ShopProductSearchRequest searchRequest, Pageable pageable) {
        Specification<ShopProduct> shopProductSpecification = searchRequest.getSpecification();

        Page<ShopProductBaseResponse> shopProducts = ShopProductMapper.INSTANCE.toPageResponse(
                this.shopProductRepository.findAll(
                        shopProductSpecification,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sorts.builder().desc("id").build())
                )
        );

        SearchPageResponse<ShopProductBaseResponse> response = new SearchPageResponse<>();
        response.setPage(shopProducts);

        if (withSearchFields != null && withSearchFields) {
            response.setSearchFields(searchRequest.getSearchFields());
        }

        List<ShopProductBaseResponse> shopProductBaseResponses = new ArrayList<>(response.getItems().size());

        for (ShopProductBaseResponse baseResponse : response.getItems()) {
            ShopCategoryBaseResponse categoryBaseResponse = baseResponse.getShopCategory();
            categoryBaseResponse.setParents(ShopCategoryMapper.INSTANCE.toShopCategoryBaseResponses(this.shopCategoryManager.getParents(categoryBaseResponse.getId())));
            baseResponse.setShopCategory(categoryBaseResponse);
            shopProductBaseResponses.add(baseResponse);
        }

        response.setItems(shopProductBaseResponses);

        return response;
    }
}
