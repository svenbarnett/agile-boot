package com.huijiewei.agile.core.manager;

import com.huijiewei.agile.core.entity.TreeEntity;

import java.util.List;

public abstract class TreeManagerCache<T extends TreeEntity<T>> {
    abstract public List<T> getAll();
}
