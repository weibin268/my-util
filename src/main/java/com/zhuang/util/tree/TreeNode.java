package com.zhuang.util.tree;

import java.util.List;

public interface TreeNode {
    String getTreeCode();

    <T extends TreeNode> List<T> getChildren();

}
