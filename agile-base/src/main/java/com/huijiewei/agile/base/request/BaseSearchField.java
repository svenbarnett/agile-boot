package com.huijiewei.agile.base.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class BaseSearchField<T extends BaseSearchField> {
    private String type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String field;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String label;

    public T field(String field) {
        this.field = field;

        return (T) this;
    }

    public T label(String label) {
        this.label = label;

        return (T) this;
    }
}
