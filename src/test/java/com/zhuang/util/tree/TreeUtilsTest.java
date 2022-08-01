package com.zhuang.util.tree;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TreeUtilsTest {

    @Data
    public static class Category implements TreeNode4Code, TreeNode4Id {

        private String code;
        private String name;
        private String parentCode;
        private List<Category> children = new ArrayList<>();

        @Override
        public String getNodeCode() {
            return code;
        }

        @Override
        public String getParentNodeId() {
            return parentCode;
        }

        @Override
        public String getNodeId() {
            return code;
        }

        @Override
        public List<Category> getChildren() {
            return children;
        }
    }

    @Test
    public void buildByTreeCode() {
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

        List<Category> categories = TreeUtils.build4Code(categoryList);
        System.out.println(categories);
    }

    @Test
    public void buildByParentId() {
        List<Category> categoryList = new ArrayList<>();
        Category c1 = new Category();
        c1.setCode("1");
        c1.setName("AA");
        c1.setParentCode("");
        categoryList.add(c1);

        Category c2 = new Category();
        c2.setCode("2");
        c2.setName("AA10");
        c2.setParentCode("1");
        categoryList.add(c2);

        Category c3 = new Category();
        c3.setCode("3");
        c3.setName("AA100");
        c3.setParentCode("2");
        categoryList.add(c3);

        List<Category> categories = TreeUtils.build4Id(categoryList);
        System.out.println(categories);
    }

}
