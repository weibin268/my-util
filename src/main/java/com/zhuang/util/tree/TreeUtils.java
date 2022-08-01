package com.zhuang.util.tree;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TreeUtils {

    public static <T extends TreeNode> List<T> build(List<T> treeCodeList) {
        List<T> oldList = treeCodeList;
        List<T> newList = new ArrayList<>();
        LinkedList<T> queue = new LinkedList<>();
        oldList.stream().sorted(Comparator.comparing(T::getTreeCode, Comparator.nullsLast(String::compareTo))).forEach(c -> queue.offer(c));
        for (int i = 0; i < oldList.size(); i++) {
            T gisLayer = queue.poll();
            List<T> parentList = new ArrayList<>();
            recursiveFindParent(newList, gisLayer, parentList);
            T parent = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);
            if (parent != null) {
                parent.getChildren().add(gisLayer);
            } else {
                newList.add(gisLayer);
            }
        }
        return newList;
    }

    private static <T extends TreeNode> void recursiveFindParent(List<T> tree, T item, List<T> result) {
        for (T parent : tree) {
            if (item.getTreeCode().startsWith(parent.getTreeCode()) && !item.getTreeCode().equals(parent.getTreeCode())) {
                result.add(parent);
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                recursiveFindParent(parent.getChildren(), item, result);
            }
        }
    }
}
