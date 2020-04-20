package com.huijiewei.agile.core.shop.mapper;

import com.huijiewei.agile.core.shop.entity.ShopProduct;
import com.huijiewei.agile.core.shop.request.ShopProductRequest;
import com.huijiewei.agile.core.shop.response.ShopProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopProductMapper {
    ShopProductMapper INSTANCE = Mappers.getMapper(ShopProductMapper.class);

    ShopProduct toShopProduct(ShopProductRequest request);

    ShopProduct toShopProduct(ShopProductRequest request, @MappingTarget ShopProduct shopProduct);

    ShopProductResponse toShopProductResponse(ShopProduct shopProduct);

    default Page<ShopProductResponse> toShopProductResponse(Page<ShopProduct> page) {
        return page.map(this::toShopProductResponse);
    }
}
