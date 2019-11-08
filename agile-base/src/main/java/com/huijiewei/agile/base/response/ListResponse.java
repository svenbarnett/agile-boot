package com.huijiewei.agile.base.response;

import lombok.Data;

import java.util.List;

@Data
public class ListResponse<E> {
    private List<E> items;

    public ListResponse<E> data(List<E> list) {
        this.setItems(list);

        return this;
    }
}
