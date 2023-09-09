package com.zhuang.util.druid;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;

import java.util.List;
import java.util.function.Supplier;

public class SassModifyBaseVisitor {

    private boolean hasModify = false;
    private String dbType;
    private List<TableInfo> tableInfoList;
    private String fieldName;
    private Supplier<String> valueSupplier;

    public SassModifyBaseVisitor(List<TableInfo> tableInfoList, String fieldName, Supplier<String> valueSupplier, String dbType) {
        if (valueSupplier.get() == null) {
            throw new RuntimeException("valueSupplier.get()返回不能为null！");
        }
        this.tableInfoList = tableInfoList;
        this.fieldName = fieldName;
        this.valueSupplier = valueSupplier;
        this.dbType = dbType;
    }

    public void visit4HasWhere(Object target, SQLTableSource tableSource) {
        if (tableSource instanceof SQLExprTableSource) {
            SQLExprTableSource exprTableSource = (SQLExprTableSource) tableSource;
            TableInfo tableInfo = getTableInfoByName(exprTableSource.getName());
            if (tableInfo != null) {
                modify4HasWhere(target, tableInfo, exprTableSource.getAlias());
            }
        } else if (tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) tableSource;
            SQLTableSource leftTableSource = joinTableSource.getLeft();
            SQLTableSource rightTableSource = joinTableSource.getRight();
            if (leftTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource leftExprTableSource = (SQLExprTableSource) leftTableSource;
                TableInfo tableInfo = getTableInfoByName(leftExprTableSource.getName());
                if (tableInfo != null) {
                    String alias = leftExprTableSource.getAlias() == null ? leftExprTableSource.getName().getSimpleName() : leftExprTableSource.getAlias();
                    modify4HasWhere(target, tableInfo, alias);
                }
            }
            if (rightTableSource instanceof SQLExprTableSource) {
                SQLExprTableSource rightExprTableSource = (SQLExprTableSource) rightTableSource;
                TableInfo tableInfo = getTableInfoByName(rightExprTableSource.getName());
                if (tableInfo != null) {
                    String alias = rightExprTableSource.getAlias() == null ? rightExprTableSource.getName().getSimpleName() : rightExprTableSource.getAlias();
                    modify4HasWhere(target, tableInfo, alias);
                }
            }
            //两个以上的表连接
            if (leftTableSource instanceof SQLJoinTableSource) {
                visit4HasWhere(target, leftTableSource);
            }
            if (rightTableSource instanceof SQLJoinTableSource) {
                visit4HasWhere(target, rightTableSource);
            }
        }
    }

    private void modify4HasWhere(Object target, TableInfo tableInfo, String alias) {
        if (target instanceof SQLSelectQueryBlock) {
            SQLSelectQueryBlock statement = (SQLSelectQueryBlock) target;
            SQLExpr whereExpr = statement.getWhere();
            if (existsPrimaryKeyInWhere(whereExpr, tableInfo.getPrimaryKey(), alias)) return;
            statement.setWhere(appendWhereExpr(whereExpr, getAppendWhere(alias)));
        } else if (target instanceof SQLUpdateStatement) {
            SQLUpdateStatement statement = (SQLUpdateStatement) target;
            SQLExpr whereExpr = statement.getWhere();
            if (existsPrimaryKeyInWhere(whereExpr, tableInfo.getPrimaryKey(), alias)) return;
            statement.setWhere(appendWhereExpr(whereExpr, getAppendWhere(alias)));
        } else if (target instanceof SQLDeleteStatement) {
            SQLDeleteStatement statement = (SQLDeleteStatement) target;
            SQLExpr whereExpr = statement.getWhere();
            if (existsPrimaryKeyInWhere(whereExpr, tableInfo.getPrimaryKey(), alias)) return;
            statement.setWhere(appendWhereExpr(whereExpr, getAppendWhere(alias)));
        }
    }

    public boolean visit4Insert(SQLInsertStatement x) {
        SQLExprTableSource tableSource = x.getTableSource();
        TableInfo tableInfo = getTableInfoByName(tableSource.getName());
        if (tableInfo == null) return true;
        List<SQLExpr> exprColumnList = x.getColumns();
        List<SQLInsertStatement.ValuesClause> valuesClauseList = x.getValuesList();
        if (exprColumnList.stream().anyMatch(c -> ((SQLIdentifierExpr) c).getName().equalsIgnoreCase(fieldName))) {
            return true;
        }
        SQLIdentifierExpr fieldExpr = new SQLIdentifierExpr(fieldName);
        exprColumnList.add(fieldExpr);
        valuesClauseList.forEach(item -> {
            SQLCharExpr valueExpr = new SQLCharExpr(valueSupplier.get());
            item.getValues().add(valueExpr);
        });
        hasModify = true;
        return true;
    }

    private TableInfo getTableInfoByName(SQLName tableName) {
        return getTableInfoByName(tableName.getSimpleName());
    }

    private TableInfo getTableInfoByName(String tableName) {
        return tableInfoList.stream().filter(c -> c.getName().equalsIgnoreCase(tableName)).findFirst().orElse(null);
    }

    private SQLExpr appendWhereExpr(SQLExpr whereExpr, String appendWhere) {
        SQLExpr result;
        SQLExprParser appendWhereParser = SQLParserUtils.createExprParser(appendWhere, dbType);
        SQLExpr appendWhereExpr = appendWhereParser.expr();
        if (whereExpr == null) {
            result = appendWhereExpr;
        } else {
            if (!existsFieldInWhere(whereExpr, ((SQLBinaryOpExpr) appendWhereExpr).getLeft())) {
                SQLBinaryOpExpr newWhereExpr = new SQLBinaryOpExpr(whereExpr, SQLBinaryOperator.BooleanAnd, appendWhereExpr);
                result = newWhereExpr;
            } else {
                result = whereExpr;
            }
        }
        if (result != whereExpr) {
            hasModify = true;
        }
        return result;
    }

    private boolean existsPrimaryKeyInWhere(SQLExpr whereExpr, String primaryKey, String alias) {
        if (primaryKey == null) return false;
        if (whereExpr instanceof SQLInListExpr) {
            SQLInListExpr sqlInListExpr = (SQLInListExpr) whereExpr;
            if (isPrimaryKeyField(sqlInListExpr.getExpr(), primaryKey, alias)) {
                return true;
            }
        } else if (whereExpr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr whereBinaryOpExpr = (SQLBinaryOpExpr) whereExpr;
            if (isPrimaryKeyField(whereBinaryOpExpr.getLeft(), primaryKey, alias)) {
                return true;
            }
            if (isPrimaryKeyField(whereBinaryOpExpr.getRight(), primaryKey, alias)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPrimaryKeyField(SQLExpr fieldExpr, String primaryKey, String alias) {
        if (fieldExpr instanceof SQLPropertyExpr) {
            SQLPropertyExpr fieldPropertyExpr = (SQLPropertyExpr) fieldExpr;
            if (fieldPropertyExpr.getName().equalsIgnoreCase(primaryKey) && fieldPropertyExpr.getOwnernName().equalsIgnoreCase(alias)) {
                return true;
            }
        } else if (fieldExpr instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr fieldIdentifierExpr = (SQLIdentifierExpr) fieldExpr;
            if (fieldIdentifierExpr.getName().equalsIgnoreCase(primaryKey)) {
                return true;
            }
        } else if (fieldExpr instanceof SQLBinaryOpExpr) {
            return existsPrimaryKeyInWhere(fieldExpr, primaryKey, alias);
        }
        return false;
    }

    private boolean existsFieldInWhere(SQLExpr whereExpr, SQLExpr appendFieldExpr) {
        if (!(whereExpr instanceof SQLBinaryOpExpr)) return false;
        SQLBinaryOpExpr whereBinaryOpExpr = (SQLBinaryOpExpr) whereExpr;
        if (sameField(whereBinaryOpExpr.getLeft(), appendFieldExpr)) {
            return true;
        }
        if (sameField(whereBinaryOpExpr.getRight(), appendFieldExpr)) {
            return true;
        }
        return false;
    }

    public boolean sameField(SQLExpr fieldExpr, SQLExpr appendFieldExpr) {
        if (fieldExpr.getClass().getName().equals(appendFieldExpr.getClass().getName())) {
            if (appendFieldExpr instanceof SQLPropertyExpr) {
                SQLPropertyExpr appendFieldPropertyExpr = (SQLPropertyExpr) appendFieldExpr;
                SQLPropertyExpr fieldPropertyExpr = (SQLPropertyExpr) fieldExpr;
                if (appendFieldPropertyExpr.getName().equalsIgnoreCase(fieldPropertyExpr.getName()) && appendFieldPropertyExpr.getOwnernName().equalsIgnoreCase(fieldPropertyExpr.getOwnernName())) {
                    return true;
                }
            } else if (appendFieldExpr instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr appendFieldIdentifierExpr = (SQLIdentifierExpr) appendFieldExpr;
                SQLIdentifierExpr fieldIdentifierExpr = (SQLIdentifierExpr) fieldExpr;
                if (appendFieldIdentifierExpr.getName().equalsIgnoreCase(fieldIdentifierExpr.getName())) {
                    return true;
                }
            }
        } else if (fieldExpr instanceof SQLBinaryOpExpr) {
            return existsFieldInWhere(fieldExpr, appendFieldExpr);
        }
        return false;
    }

    private String getAppendWhere(String alias) {
        String result = fieldName + " = '" + valueSupplier.get() + "'";
        if (alias != null) {
            result = alias + "." + result;
        }
        return result;
    }

    public boolean hasModify() {
        return this.hasModify;
    }

    public String getDbType() {
        return dbType;
    }

    public static class TableInfo {
        private String name;
        private String primaryKey;

        public TableInfo(String name, String primaryKey) {
            this.name = name;
            this.primaryKey = primaryKey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(String primaryKey) {
            this.primaryKey = primaryKey;
        }
    }
}
