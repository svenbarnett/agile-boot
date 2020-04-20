package com.huijiewei.agile.core.shop.mapper;

import com.huijiewei.agile.core.shop.entity.ShopBrand;
import com.huijiewei.agile.core.shop.request.ShopBrandRequest;
import com.huijiewei.agile.core.shop.response.ShopBrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopBrandMapper {
    ShopBrandMapper INSTANCE = Mappers.getMapper(ShopBrandMapper.class);

    ShopBrand toShopBrand(ShopBrandRequest request);

    ShopBrand toShopBrand(ShopBrandRequest request, @MappingTarget ShopBrand shopBrand);

    ShopBrandResponse toShopBrandResponse(ShopBrand shopBrand);

    List<ShopBrandResponse> toShopBrandResponses(List<ShopBrand> shopBrands);

    default Page<ShopBrandResponse> toShopBrandResponses(Page<ShopBrand> page) {
        return page.map(this::toShopBrandResponse);
    }

}
