package com.zhuang.util.druid;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;

import java.util.List;

public class ShardingModifyBaseVisitor {

    private boolean hasModify = false;
    private String dbType;
    private List<TableInfo> tableInfoList;

    public ShardingModifyBaseVisitor(List<TableInfo> tableInfoList, String dbType) {

        this.tableInfoList = tableInfoList;

        this.dbType = dbType;
    }

    public void visit(Object target, SQLTableSource tableSource) {
        if (tableSource instanceof SQLExprTableSource) {
            SQLExprTableSource exprTableSource = (SQLExprTableSource) tableSource;
            TableInfo tableInfo = getTableInfoByName(exprTableSource.getName());
            if (tableInfo != null) {
                modify(exprTableSource, tableInfo);
            }
        } else if (tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) tableSource;
            SQLTableSource leftTableSource = joinTableSource.getLeft();
            SQLTableSource rightTableSource = joinTableSource.getRight();
            if (leftTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource leftExprTableSource = (SQLExprTableSource) leftTableSource;
                TableInfo tableInfo = getTableInfoByName(leftExprTableSource.getName());
                if (tableInfo != null) {
                    modify(leftExprTableSource, tableInfo);
                }
            }
            if (rightTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource rightExprTableSource = (SQLExprTableSource) rightTableSource;
                TableInfo tableInfo = getTableInfoByName(rightExprTableSource.getName());
                if (tableInfo != null) {
                    modify(rightExprTableSource, tableInfo);
                }
            }
            //两个以上的表连接
            if (leftTableSource instanceof SQLJoinTableSource) {
                visit(target, leftTableSource);
            }
            if (rightTableSource instanceof SQLJoinTableSource) {
                visit(target, rightTableSource);
            }
        }
    }

    private void modify(SQLExprTableSource exprTableSource, TableInfo tableInfo) {
        if (tableInfo.getShardingName() == null) return;
        exprTableSource.setExpr(tableInfo.getName() + tableInfo.getDelimiter() + tableInfo.getShardingName());
        hasModify = true;
    }

    private TableInfo getTableInfoByName(SQLName tableName) {
        return getTableInfoByName(tableName.getSimpleName());
    }

    private TableInfo getTableInfoByName(String tableName) {
        return tableInfoList.stream().filter(c -> c.getName().equalsIgnoreCase(tableName)).findFirst().orElse(null);
    }

    public boolean hasModify() {
        return this.hasModify;
    }

    public String getDbType() {
        return dbType;
    }

    public static class TableInfo {
        private String name;
        private String delimiter;

        public TableInfo(String name) {
            this(name, "_");
        }

        public TableInfo(String name, String delimiter) {
            this.name = name;
            this.delimiter = delimiter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShardingName() {
            return ShardingNameHolder.getShardingName(name);
        }

        public String getDelimiter() {
            return delimiter;
        }

        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }
    }
}
