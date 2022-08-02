package com.zhuang.util.tree;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtils {

    public static <T extends TreeNode4Code> List<T> build4Code(List<T> nodeList) {
        List<T> newNodeList = new ArrayList<>();
        nodeList = nodeList.stream().sorted(Comparator.comparing(T::getNodeCode, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
        for (T node : nodeList) {
            T parent = recursiveFindParent4Code(newNodeList, node);
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                newNodeList.add(node);
            }
        }
        return newNodeList;
    }

    public static <T extends TreeNode4Id> List<T> build4Id(List<T> nodeList) {
        return build4Id(nodeList, 10);
    }

    public static <T extends TreeNode4Id> List<T> build4Id(List<T> nodeList, int maxDepth) {
        List<T> newNodeList = new ArrayList<>();
        for (T node : nodeList) {
            T parent = recursiveFindParent4Id(nodeList, node, 1, maxDepth);
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                newNodeList.add(node);
            }
        }
        return newNodeList;
    }

    private static <T extends TreeNode4Code> T recursiveFindParent4Code(List<T> tree, T item) {
        for (T parent : tree) {
            if (item.getNodeCode().startsWith(parent.getNodeCode()) && !item.getNodeCode().equals(parent.getNodeCode())) {
                return parent;
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                T p = recursiveFindParent4Code(parent.getChildren(), item);
                if (p != null) return p;
            }
        }
        return null;
    }

    private static <T extends TreeNode4Id> T recursiveFindParent4Id(List<T> tree, T item, int depth, int maxDepth) {
        if (depth > maxDepth) return null;
        for (T parent : tree) {
            if (parent.getNodeId().equals(item.getParentNodeId())) {
                return parent;
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                T p = recursiveFindParent4Id(parent.getChildren(), item, ++depth, maxDepth);
                if (p != null) return p;
            }
        }
        return null;
    }
}
