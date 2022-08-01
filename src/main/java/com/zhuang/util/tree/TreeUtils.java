package com.zhuang.util.tree;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtils {

    public static <T extends TreeNode4Code> List<T> build4Code(List<T> treeNodeList) {
        List<T> oldNodeList = treeNodeList;
        List<T> newNodeList = new ArrayList<>();
        oldNodeList = oldNodeList.stream().sorted(Comparator.comparing(T::getNodeCode, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
        for (T node : oldNodeList) {
            List<T> parentList = new ArrayList<>();
            recursiveFindParent4Code(newNodeList, node, parentList);
            T parent = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                newNodeList.add(node);
            }
        }
        return newNodeList;
    }

    public static <T extends TreeNode4Id> List<T> build4Id(List<T> treeNodeList) {
        List<T> oldNodeList = treeNodeList;
        List<T> newNodeList = new ArrayList<>();
        for (T node : oldNodeList) {
            List<T> parentList = new ArrayList<>();
            recursiveFindParent4Id(newNodeList, node, parentList);
            T parent = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                newNodeList.add(node);
            }
        }
        return newNodeList;
    }

    private static <T extends TreeNode4Code> void recursiveFindParent4Code(List<T> tree, T item, List<T> result) {
        for (T parent : tree) {
            if (item.getNodeCode().startsWith(parent.getNodeCode()) && !item.getNodeCode().equals(parent.getNodeCode())) {
                result.add(parent);
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                recursiveFindParent4Code(parent.getChildren(), item, result);
            }
        }
    }

    private static <T extends TreeNode4Id> void recursiveFindParent4Id(List<T> tree, T item, List<T> result) {
        for (T parent : tree) {
            if (item.getParentNodeId().equals(parent.getNodeId())) {
                result.add(parent);
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                recursiveFindParent4Id(parent.getChildren(), item, result);
            }
        }
    }
}
