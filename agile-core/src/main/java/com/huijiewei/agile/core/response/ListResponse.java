package com.huijiewei.agile.core.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ListResponse<E> {
    @Schema(description = "记录列表")
    private List<E> items;

    public List<E> getItems() {
        return this.items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }
}
