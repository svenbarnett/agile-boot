package com.huijiewei.agile.core.shop.entity;

import com.huijiewei.agile.core.constraint.Unique;
import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Unique.List({
        @Unique(fields = {"name"}, message = "品牌已存在"),
        @Unique(fields = {"alias"}, message = "品牌别名已被占用")
})
public class ShopBrand extends BaseEntity {
    private String name;

    private String alias;

    private String logo;

    private String description;

    @OneToMany
    @JoinTable(
            name = "ShopBrandCategory",
            joinColumns = @JoinColumn(name = "shopBrandId", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "shopCategoryId", insertable = false, updatable = false)
    )
    @Setter(AccessLevel.NONE)
    private List<ShopCategory> shopCategories;
}
