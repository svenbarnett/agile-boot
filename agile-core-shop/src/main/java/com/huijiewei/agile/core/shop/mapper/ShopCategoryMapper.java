package com.huijiewei.agile.core.shop.mapper;

import com.huijiewei.agile.core.shop.entity.ShopCategory;
import com.huijiewei.agile.core.shop.request.ShopCategoryRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryBaseResponse;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
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

    @Mapping(target = "parents", ignore = true)
    ShopCategoryBaseResponse toShopCategoryBaseResponse(ShopCategory category);

    default List<ShopCategoryBaseResponse> toShopCategoryBaseResponses(List<ShopCategory> categories) {
        if (categories == null) {
            return null;
        } else {
            List<ShopCategoryBaseResponse> list = new ArrayList<>(categories.size());

            for (ShopCategory shopCategory : categories) {
                list.add(this.toShopCategoryBaseResponse(shopCategory));
            }

            return list;
        }
    }
}
