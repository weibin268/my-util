package com.zhuang.util.druid;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.function.Supplier;

public class SassModifyMySqlVisitor extends MySqlASTVisitorAdapter {

    private SassModifyBaseVisitor baseVisitor;

    public SassModifyBaseVisitor getBaseVisitor() {
        return baseVisitor;
    }

    public SassModifyMySqlVisitor(List<SassModifyBaseVisitor.TableInfo> tableInfoList, String fieldName, Supplier<String> valueSupplier) {
        baseVisitor = new SassModifyBaseVisitor(tableInfoList, fieldName, valueSupplier, JdbcConstants.MYSQL);
    }

    @Override
    public boolean visit(MySqlSelectQueryBlock x) {
        baseVisitor.visit4HasWhere(x, x.getFrom());
        return true;
    }

    @Override
    public boolean visit(MySqlUpdateStatement x) {
        baseVisitor.visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(MySqlDeleteStatement x) {
        baseVisitor.visit4HasWhere(x, x.getTableSource());
        return true;
    }

    @Override
    public boolean visit(MySqlInsertStatement x) {
        return baseVisitor.visit4Insert(x);
    }

}
