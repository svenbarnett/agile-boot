package com.huijiewei.agile.base.response;

import com.huijiewei.agile.base.request.BaseSearchField;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SearchListResponse<E> extends ListResponse<E> {
    @Schema(description = "搜索字段信息")
    private List<BaseSearchField> searchFields;

    public List<BaseSearchField> getSearchFields() {
        return this.searchFields;
    }

    public void setSearchFields(List<BaseSearchField> searchFields) {
        this.searchFields = searchFields;
    }
}
