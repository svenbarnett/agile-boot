package com.huijiewei.agile.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Data
@MappedSuperclass
public abstract class BaseSearchRequest {
    @Schema(description = "搜索字段")
    private String field;

    @Schema(description = "搜索关键字")
    private String keyword;

    @Schema(hidden = true)
    private List<BaseSearchField> searchFields;

    public BaseSearchRequest addSearchField(BaseSearchField searchField) {
        if (this.searchFields == null) {
            this.searchFields = new ArrayList<>();
        }

        this.searchFields.add(searchField);

        return this;
    }
}
