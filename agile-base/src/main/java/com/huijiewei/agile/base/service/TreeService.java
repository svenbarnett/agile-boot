package com.huijiewei.agile.base.service;

import com.huijiewei.agile.base.entity.TreeEntity;

import java.util.*;

public abstract class TreeService<T extends TreeEntity> {
    protected List<T> buildTree(List<T> items) {
        Map<Integer, T> map = new HashMap<>();

        for (T current : items) {
            map.put(current.getId(), current);
        }

        List<T> tree = new ArrayList<>();

        for (T item : items) {
            final Integer parentId = item.getParentId();

            if (parentId != 0) {
                final T child = map.get(item.getId());
                final T parent = map.get(parentId);

                if (parent != null) {
                    parent.addChild(child);
                }
            } else {
                tree.add(item);
            }
        }

        return tree;
    }

    protected List<T> buildParents(Integer id, List<T> items) {
        List<T> parents = new ArrayList<>();

        T parent = this.getItemInItemsById(id, items);

        while (parent != null) {
            parents.add(parent);
            parent = this.getItemInItemsById(parent.getParentId(), items);
        }

        Collections.reverse(parents);

        return parents;
    }

    private T getItemInItemsById(Integer id, List<T> items) {
        for (T item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }
}
