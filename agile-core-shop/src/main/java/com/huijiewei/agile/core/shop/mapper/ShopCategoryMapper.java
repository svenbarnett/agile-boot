package com.huijiewei.agile.core.shop.mapper;

import com.huijiewei.agile.core.shop.entity.ShopCategory;
import com.huijiewei.agile.core.shop.request.ShopCategoryRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShopCategoryMapper {
    ShopCategoryMapper INSTANCE = Mappers.getMapper(ShopCategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "children", ignore = true)
    ShopCategory toShopCategory(ShopCategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "children", ignore = true)
    ShopCategory toShopCategory(ShopCategoryRequest request, @MappingTarget ShopCategory shopCategory);

    @Mapping(target = "parents", ignore = true)
    ShopCategoryResponse toShopCategoryResponse(ShopCategory category);

    List<ShopCategoryResponse> toShopCategoryResponses(List<ShopCategory> categories);
}
