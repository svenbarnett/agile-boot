package com.huijiewei.agile.core.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValueDescription<T> {
    private final T value;
    private final String description;
}
