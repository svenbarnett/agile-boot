package com.huijiewei.agile.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class TreeEntity<T extends TreeEntity<T>> extends BaseEntity {
    @NotNull
    private Integer parentId;

    @Transient
    private List<T> children;

    public void addChild(T child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }

        this.children.add(child);
    }
}
