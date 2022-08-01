package com.zhuang.util.tree;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TreeUtils {

    public static <T extends TreeCodeNode> List<T> buildByTreeCode(List<T> treeCodeList) {
        List<T> oldList = treeCodeList;
        List<T> newList = new ArrayList<>();
        LinkedList<T> queue = new LinkedList<>();
        oldList.stream().sorted(Comparator.comparing(T::getTreeCode, Comparator.nullsLast(String::compareTo))).forEach(c -> queue.offer(c));
        for (int i = 0; i < oldList.size(); i++) {
            T node = queue.poll();
            List<T> parentList = new ArrayList<>();
            recursiveFindParentByTreeCode(newList, node, parentList);
            T parent = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                newList.add(node);
            }
        }
        return newList;
    }

    public static <T extends ParentIdNode> List<T> buildByParentId(List<T> treeCodeList) {
        List<T> oldList = treeCodeList;
        List<T> newList = new ArrayList<>();
        LinkedList<T> queue = new LinkedList<>();
        queue.addAll(oldList);
        for (int i = 0; i < oldList.size(); i++) {
            T node = queue.poll();
            List<T> parentList = new ArrayList<>();
            recursiveFindParentByParentId(newList, node, parentList);
            T parent = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                newList.add(node);
            }
        }
        return newList;
    }

    private static <T extends TreeCodeNode> void recursiveFindParentByTreeCode(List<T> tree, T item, List<T> result) {
        for (T parent : tree) {
            if (item.getTreeCode().startsWith(parent.getTreeCode()) && !item.getTreeCode().equals(parent.getTreeCode())) {
                result.add(parent);
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                recursiveFindParentByTreeCode(parent.getChildren(), item, result);
            }
        }
    }

    private static <T extends ParentIdNode> void recursiveFindParentByParentId(List<T> tree, T item, List<T> result) {
        for (T parent : tree) {
            if (item.getParentId().equals(parent.getId())) {
                result.add(parent);
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                recursiveFindParentByParentId(parent.getChildren(), item, result);
            }
        }
    }
}
