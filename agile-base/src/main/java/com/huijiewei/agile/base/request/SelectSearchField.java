package com.huijiewei.agile.base.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelectSearchField extends BaseSearchField<SelectSearchField> {
    private Boolean multiple;
    private Map<String, String> options;

    public SelectSearchField() {
        this.setType("select");
    }

    public SelectSearchField multiple(Boolean multiple) {
        this.multiple = multiple;

        return this;
    }

    public SelectSearchField options(Map<String, String> options) {
        this.options = options;

        return this;
    }

    public SelectSearchField addOption(String key, String value) {
        if (this.options == null) {
            this.options = new HashMap<>();
        }

        this.options.put(key, value);

        return this;
    }
}
