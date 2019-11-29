package com.huijiewei.agile.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huijiewei.agile.core.request.BaseSearchField;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SearchPageResponse<E> extends PageResponse<E> {
    @Schema(description = "搜索字段信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BaseSearchField<?>> searchFields;

    public List<BaseSearchField<?>> getSearchFields() {
        return this.searchFields;
    }

    public void setSearchFields(List<BaseSearchField<?>> searchFields) {
        this.searchFields = searchFields;
    }
}
