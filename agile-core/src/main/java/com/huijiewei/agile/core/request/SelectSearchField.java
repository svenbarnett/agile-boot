package com.huijiewei.agile.core.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelectSearchField extends BaseSearchField<SelectSearchField> {
    private Boolean multiple = false;
    private Map<?, String> options;

    public SelectSearchField() {
        this.setType("select");
    }

    public SelectSearchField multiple(Boolean multiple) {
        this.multiple = multiple;

        return this;
    }

    public SelectSearchField options(Map<?, String> options) {
        this.options = options;

        return this;
    }

    @Override
    protected SelectSearchField self() {
        return this;
    }
}
