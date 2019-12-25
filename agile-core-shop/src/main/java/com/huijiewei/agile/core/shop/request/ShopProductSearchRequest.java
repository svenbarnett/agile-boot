package com.huijiewei.agile.core.shop.request;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.huijiewei.agile.core.request.BaseSearchRequest;
import com.huijiewei.agile.core.request.KeywordSearchField;
import com.huijiewei.agile.core.shop.entity.ShopProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopProductSearchRequest extends BaseSearchRequest {
    private String name;
    private Integer shopCategoryId;
    private Integer shopBrandId;

    public ShopProductSearchRequest() {
        this
                .addSearchField(new KeywordSearchField().field("name").label("商品名称"));
    }

    public Specification<ShopProduct> getSpecification() {
        // READ https://github.com/wenhao/jpa-spec/blob/master/README_CN.md
        PredicateBuilder<ShopProduct> predicateBuilder = Specifications.<ShopProduct>and()
                .like(StringUtils.isNotEmpty(this.name), "name", '%' + this.name + '%');

        return predicateBuilder.build();
    }
}
