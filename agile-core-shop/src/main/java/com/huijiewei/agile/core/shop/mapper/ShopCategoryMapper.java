package com.huijiewei.agile.core.shop.mapper;

import com.huijiewei.agile.core.shop.entity.ShopCategory;
import com.huijiewei.agile.core.shop.request.ShopCategoryRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopCategoryMapper {
    ShopCategoryMapper INSTANCE = Mappers.getMapper(ShopCategoryMapper.class);

    ShopCategory toShopCategory(ShopCategoryRequest request);

    ShopCategory toShopCategory(ShopCategoryRequest request, @MappingTarget ShopCategory shopCategory);

    ShopCategoryResponse toShopCategoryResponse(ShopCategory category);

    List<ShopCategoryResponse> toShopCategoryResponses(List<ShopCategory> shopCategories);
}
