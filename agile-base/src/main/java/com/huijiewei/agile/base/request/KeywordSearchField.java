package com.huijiewei.agile.base.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeywordSearchField extends BaseSearchField<KeywordSearchField> {
    public KeywordSearchField() {
        this.setType("keyword");
    }

    @Override
    protected KeywordSearchField self() {
        return this;
    }
}
