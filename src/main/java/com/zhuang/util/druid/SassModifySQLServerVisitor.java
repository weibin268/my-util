package com.zhuang.util.druid;

import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerSelectQueryBlock;
import com.alibaba.druid.sql.dialect.sqlserver.ast.stmt.SQLServerInsertStatement;
import com.alibaba.druid.sql.dialect.sqlserver.ast.stmt.SQLServerUpdateStatement;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.function.Supplier;

public class SassModifySQLServerVisitor extends SQLServerASTVisitorAdapter {

    private SassModifyBaseVisitor baseVisitor;

    public SassModifyBaseVisitor getBaseVisitor() {
        return baseVisitor;
    }

    public SassModifySQLServerVisitor(List<SassModifyBaseVisitor.TableInfo> tableInfoList, String fieldName, Supplier<String> valueSupplier) {
        baseVisitor = new SassModifyBaseVisitor(tableInfoList, fieldName, valueSupplier, JdbcConstants.SQL_SERVER);
    }

    @Override
    public boolean visit(SQLServerSelectQueryBlock x) {
        baseVisitor.visit4HasWhere(x, x.getFrom());
        return true;
    }

    @Override
    public boolean visit(SQLServerUpdateStatement x) {
        baseVisitor.visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(SQLDeleteStatement x) {
        baseVisitor.visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(SQLServerInsertStatement x) {
        return baseVisitor.visit4Insert(x);
    }


}
