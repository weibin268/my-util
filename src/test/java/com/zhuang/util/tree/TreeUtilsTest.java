package com.zhuang.util.tree;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TreeUtilsTest {

    @Data
    public static class Category implements TreeNode {

        private String code;
        private String name;
        private List<Category> children = new ArrayList<>();

        @Override
        public String getTreeCode() {
            return code;
        }

        @Override
        public List<Category> getChildren() {
            return children;
        }
    }

    @Test
    public void build() {
        List<Category> categoryList = new ArrayList<>();
        Category c1 = new Category();
        c1.setCode("10");
        c1.setName("AA");
        categoryList.add(c1);

        Category c2 = new Category();
        c2.setCode("1010");
        c2.setName("AA10");
        categoryList.add(c2);

        Category c3 = new Category();
        c3.setCode("10100");
        c3.setName("AA100");
        categoryList.add(c3);

        List<Category> categories = TreeUtils.build(categoryList);
        System.out.println(categories);
    }

}
