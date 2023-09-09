package com.zhuang.util.druid;

import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerSelectQueryBlock;
import com.alibaba.druid.sql.dialect.sqlserver.ast.stmt.SQLServerInsertStatement;
import com.alibaba.druid.sql.dialect.sqlserver.ast.stmt.SQLServerUpdateStatement;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

public class ShardingModifySQLServerVisitor extends SQLServerASTVisitorAdapter {

    private ShardingModifyBaseVisitor baseVisitor;

    public ShardingModifyBaseVisitor getBaseVisitor() {
        return baseVisitor;
    }

    public ShardingModifySQLServerVisitor(List<ShardingModifyBaseVisitor.TableInfo> tableInfoList ) {
        baseVisitor = new ShardingModifyBaseVisitor(tableInfoList,   JdbcConstants.SQL_SERVER);
    }

    @Override
    public boolean visit(SQLServerSelectQueryBlock x) {
        baseVisitor.visit(x, x.getFrom());
        return true;
    }

    @Override
    public boolean visit(SQLServerUpdateStatement x) {
        baseVisitor.visit(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(SQLDeleteStatement x) {
        baseVisitor.visit(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(SQLServerInsertStatement x) {
        baseVisitor.visit(x, x.getTableSource());
        return true;
    }


}
