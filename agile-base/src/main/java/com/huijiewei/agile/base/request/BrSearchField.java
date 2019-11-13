package com.huijiewei.agile.base.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BrSearchField extends BaseSearchField<BrSearchField> {
    public BrSearchField() {
        this.setType("br");
    }

    @Override
    protected BrSearchField self() {
        return this;
    }
}
