package com.huijiewei.agile.core.shop.mapper;

import com.huijiewei.agile.core.shop.entity.ShopBrand;
import com.huijiewei.agile.core.shop.request.ShopBrandRequest;
import com.huijiewei.agile.core.shop.response.ShopBrandBaseResponse;
import com.huijiewei.agile.core.shop.response.ShopBrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopBrandMapper {
    ShopBrandMapper INSTANCE = Mappers.getMapper(ShopBrandMapper.class);

    ShopBrand toShopBrand(ShopBrandRequest request);

    ShopBrand toShopBrand(ShopBrandRequest request, @MappingTarget ShopBrand shopBrand);

    ShopBrandResponse toShopBrandResponse(ShopBrand shopBrand);

    ShopBrandBaseResponse toShopBrandBaseResponse(ShopBrand shopBrand);

    default List<ShopBrandResponse> toShopBrandResponses(List<ShopBrand> shopBrands) {
        if (shopBrands == null) {
            return null;
        }

        List<ShopBrandResponse> list = new ArrayList<>(shopBrands.size());

        for (ShopBrand shopBrand : shopBrands) {
            list.add(this.toShopBrandResponse(shopBrand));
        }

        return list;
    }

    default List<ShopBrandBaseResponse> toShopBrandBaseResponses(List<ShopBrand> shopBrands) {
        if (shopBrands == null) {
            return null;
        }

        List<ShopBrandBaseResponse> list = new ArrayList<>(shopBrands.size());

        for (ShopBrand shopBrand : shopBrands) {
            list.add(this.toShopBrandBaseResponse(shopBrand));
        }

        return list;
    }
}
