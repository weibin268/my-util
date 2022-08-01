package com.zhuang.util.tree;

import java.util.List;

public interface TreeNode4Id {

    /**
     * 父节点的Id
     *
     * @return
     */
    String getParentNodeId();

    /**
     * 节点的Id
     *
     * @return
     */
    String getNodeId();

    /**
     * 子节点的集合
     *
     * @param <T>
     * @return
     */
    <T extends TreeNode4Id> List<T> getChildren();

}
