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

    public static <T extends TreeNode4Id> List<T> build4Id(List<T> nodeList) {
        List<T> newNodeList = new ArrayList<>();
        for (T node : nodeList) {
            T parent = findParent4Id(nodeList, node);
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                newNodeList.add(node);
            }
        }
        return newNodeList;
    }

    private static <T extends TreeNode4Code> void recursiveFindParent4Code(List<T> tree, T node, List<T> result) {
        for (T parent : tree) {
            if (node.getNodeCode().startsWith(parent.getNodeCode()) && !node.getNodeCode().equals(parent.getNodeCode())) {
                result.add(parent);
            }
            if (!CollectionUtils.isEmpty(parent.getChildren())) {
                recursiveFindParent4Code(parent.getChildren(), node, result);
            }
        }
    }

    private static <T extends TreeNode4Id> T findParent4Id(List<T> tree, T node) {
        for (T parent : tree) {
            if (parent.getNodeId().equals(node.getParentNodeId())) {
                return parent;
            }
        }
        return null;
    }

}
