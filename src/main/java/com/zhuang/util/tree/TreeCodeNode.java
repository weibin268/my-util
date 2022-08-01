package com.zhuang.util.tree;

import java.util.List;

public interface TreeCodeNode {

    /**
     * 可构建树的编号（格式：10或1010或101010）
     *
     * @return
     */
    String getTreeCode();

    /**
     * 子节点的集合
     *
     * @param <T>
     * @return
     */
    <T extends TreeCodeNode> List<T> getChildren();

}
