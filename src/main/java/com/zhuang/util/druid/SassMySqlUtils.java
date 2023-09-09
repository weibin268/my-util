package com.zhuang.util.druid;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;

import java.util.List;
import java.util.function.Supplier;

public class SassMySqlUtils {

    public static String parseSql(String sql, List<SassModifyBaseVisitor.TableInfo> tableInfoList, String fieldName, Supplier<String> valueSupplier) {
        return parseSql(sql, new SassModifyMySqlVisitor(tableInfoList, fieldName, valueSupplier));
    }

    public static String parseSql(String sql,SassModifyMySqlVisitor sassModifyMySqlVisitor) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, sassModifyMySqlVisitor.getBaseVisitor().getDbType());
        List<SQLStatement> sqlStatementList = sqlStatementParser.parseStatementList();
        sqlStatementList.forEach(c -> c.accept(sassModifyMySqlVisitor));
        if (sassModifyMySqlVisitor.getBaseVisitor().hasModify()) {
            StringBuilder sbSql = new StringBuilder();
            sqlStatementList.forEach(c -> sbSql.append('\n').append(c.toString()));
            return sbSql.toString();
        } else {
            return null;
        }
    }
}
