package com.huijiewei.agile.core.shop.service;

import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.core.response.SearchPageResponse;
import com.huijiewei.agile.core.shop.entity.ShopProduct;
import com.huijiewei.agile.core.shop.manager.ShopCategoryManager;
import com.huijiewei.agile.core.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.core.shop.mapper.ShopProductMapper;
import com.huijiewei.agile.core.shop.repository.ShopProductRepository;
import com.huijiewei.agile.core.shop.request.ShopProductSearchRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import com.huijiewei.agile.core.shop.response.ShopProductResponse;
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

    public SearchPageResponse<ShopProductResponse> getAll(Boolean withSearchFields, ShopProductSearchRequest searchRequest, Pageable pageable) {
        Specification<ShopProduct> shopProductSpecification = searchRequest.getSpecification();

        Page<ShopProductResponse> shopProducts = ShopProductMapper.INSTANCE.toShopProductResponse(
                this.shopProductRepository.findAll(
                        shopProductSpecification,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sorts.builder().desc("id").build())
                )
        );

        SearchPageResponse<ShopProductResponse> response = new SearchPageResponse<>();
        response.setPage(shopProducts);

        if (withSearchFields != null && withSearchFields) {
            response.setSearchFields(searchRequest.getSearchFields());
        }

        List<ShopProductResponse> shopProductResponses = new ArrayList<>(response.getItems().size());

        for (ShopProductResponse shopProductResponse : response.getItems()) {
            ShopCategoryResponse categoryResponse = shopProductResponse.getShopCategory();
            categoryResponse.setParents(ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.shopCategoryManager.getParents(categoryResponse.getId())));
            shopProductResponse.setShopCategory(categoryResponse);
            shopProductResponses.add(shopProductResponse);
        }

        response.setItems(shopProductResponses);

        return response;
    }
}
