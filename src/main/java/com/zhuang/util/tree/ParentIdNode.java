package com.zhuang.util.tree;

import java.util.List;

public interface ParentIdNode {

    /**
     * 父节点的Id
     *
     * @return
     */
    String getParentId();

    /**
     * 节点的Id
     *
     * @return
     */
    String getId();

    /**
     * 子节点的集合
     *
     * @param <T>
     * @return
     */
    <T extends ParentIdNode> List<T> getChildren();

}
