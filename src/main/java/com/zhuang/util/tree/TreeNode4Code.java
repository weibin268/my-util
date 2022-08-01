package com.zhuang.util.tree;

import java.util.List;

public interface TreeNode4Code {

    /**
     * 可构建树的编号（格式：10或1010或101010）
     *
     * @return
     */
    String getNodeCode();

    /**
     * 子节点的集合
     *
     * @param <T>
     * @return
     */
    <T extends TreeNode4Code> List<T> getChildren();

}
