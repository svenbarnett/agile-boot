package com.huijiewei.agile.base.service;

import com.huijiewei.agile.base.entity.TreeEntity;

import java.util.*;

@SuppressWarnings("unchecked")
public abstract class TreeService<T extends TreeEntity> {
    abstract public List<T> findAll();

    abstract public List<T> findTree();

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

        T parent = this.getItemInListById(id, items);

        while (parent != null) {
            parents.add(parent);
            parent = this.getItemInListById(parent.getParentId(), items);
        }

        Collections.reverse(parents);

        return parents;
    }

    protected List<T> buildChildren(Integer id, List<T> tree) {
        T node = this.getNodeInTreeById(id, tree);

        if (node != null && node.getChildren() != null) {
            return node.getChildren();
        }
        return new ArrayList<>();
    }

    protected List<Integer> getChildrenIdsById(Integer id, List<T> tree) {
        return this.getChildrenIdsById(id, tree, false);
    }

    protected List<Integer> getChildrenIdsById(Integer id, List<T> tree, Boolean withOwner) {
        List<T> children = this.buildChildren(id, tree);

        List<Integer> childrenIds = this.getNodeIdsInTree(children);

        if (withOwner) {
            childrenIds.add(1, id);
        }

        return childrenIds;
    }

    private List<Integer> getNodeIdsInTree(List<T> tree) {
        List<Integer> ids = new ArrayList<>();

        for (T node : tree) {
            ids.add(node.getId());

            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                ids.addAll(this.getNodeIdsInTree(node.getChildren()));
            }
        }

        return ids;
    }

    private T getNodeInTreeById(Integer id, List<T> tree) {
        T result = null;

        for (T node : tree) {
            if (result != null) {
                break;
            }

            if (node.getId().equals(id)) {
                result = node;
                break;
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                result = (T) this.getNodeInTreeById(id, node.getChildren());
            }
        }

        return result;
    }

    private T getItemInListById(Integer id, List<T> list) {
        for (T item : list) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }
}
