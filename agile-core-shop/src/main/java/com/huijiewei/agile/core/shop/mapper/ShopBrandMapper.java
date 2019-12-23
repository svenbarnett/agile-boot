package com.huijiewei.agile.core.shop.mapper;

import com.huijiewei.agile.core.shop.entity.ShopBrand;
import com.huijiewei.agile.core.shop.request.ShopBrandRequest;
import com.huijiewei.agile.core.shop.response.ShopBrandBaseResponse;
import com.huijiewei.agile.core.shop.response.ShopBrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ShopBrandMapper {
    ShopBrandMapper INSTANCE = Mappers.getMapper(ShopBrandMapper.class);

    @Mapping(target = "id", ignore = true)
    ShopBrand toShopBrand(ShopBrandRequest request);

    @Mapping(target = "id", ignore = true)
    ShopBrand toShopBrand(ShopBrandRequest request, @MappingTarget ShopBrand shopBrand);

    ShopBrandResponse toShopBrandResponse(ShopBrand brand);

    ShopBrandBaseResponse toShopBrandBaseResponse(ShopBrand brand);

    default List<ShopBrandResponse> toShopBrandResponses(List<ShopBrand> brands) {
        if (brands == null) {
            return null;
        } else {
            List<ShopBrandResponse> list = new ArrayList<>(brands.size());

            for (ShopBrand shopBrand : brands) {
                list.add(this.toShopBrandResponse(shopBrand));
            }

            return list;
        }
    }

    default List<ShopBrandBaseResponse> toShopBrandBaseResponses(List<ShopBrand> brands) {
        if (brands == null) {
            return null;
        } else {
            List<ShopBrandBaseResponse> list = new ArrayList<>(brands.size());

            for (ShopBrand shopBrand : brands) {
                list.add(this.toShopBrandBaseResponse(shopBrand));
            }

            return list;
        }
    }
}
