package com.huijiewei.agile.core.manager;

import com.huijiewei.agile.core.entity.TreeEntity;

import java.util.*;

public abstract class TreeManager<T extends TreeEntity<T>> {
    private static <T extends TreeEntity<T>> T getItemInListById(Integer id, List<T> list) {
        for (T item : list) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    private static <T extends TreeEntity<T>> List<Integer> getNodeIdsInTree(List<T> tree) {
        List<Integer> ids = new ArrayList<>();

        for (T node : tree) {
            ids.add(node.getId());

            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                ids.addAll(TreeManager.getNodeIdsInTree(node.getChildren()));
            }
        }

        return ids;
    }

    private static <T extends TreeEntity<T>> T getNodeInTreeById(Integer id, List<T> tree) {
        T result = null;

        for (T node : tree) {
            if (result != null) {
                break;
            }

            if (node.getId().equals(id)) {
                result = node;
                break;
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                result = (T) TreeManager.getNodeInTreeById(id, node.getChildren());
            }
        }

        return result;
    }

    abstract public List<T> getAll();

    public List<T> getTree() {
        Map<Integer, T> map = new HashMap<>();

        List<T> items = this.getAll();

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

    public List<T> getParents(Integer id) {
        List<T> items = this.getAll();

        List<T> parents = new ArrayList<>();

        T parent = TreeManager.getItemInListById(id, items);

        while (parent != null) {
            parents.add(parent);
            parent = TreeManager.getItemInListById(parent.getParentId(), items);
        }

        Collections.reverse(parents);

        return parents;
    }

    public List<T> getChildren(Integer id) {
        T node = TreeManager.getNodeInTreeById(id, this.getTree());

        if (node != null && node.getChildren() != null) {
            return node.getChildren();
        }

        return new ArrayList<>();
    }

    public List<Integer> getChildrenIds(Integer id, Boolean withOwner) {
        List<T> children = this.getChildren(id);

        List<Integer> childrenIds = TreeManager.getNodeIdsInTree(children);

        if (withOwner) {
            childrenIds.add(0, id);
        }

        return childrenIds;
    }
}
