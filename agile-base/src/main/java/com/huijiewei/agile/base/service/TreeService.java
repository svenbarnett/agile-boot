package com.huijiewei.agile.base.service;

import com.huijiewei.agile.base.entity.TreeEntity;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
public abstract class TreeService<T extends TreeEntity, R extends JpaRepository<T, Integer>> {
    abstract protected R repository();

    public List<T> getTree() {
        List<T> items = this.repository().findAll();

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
}
