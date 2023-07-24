package com.zhuang.util.druid;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;

import java.util.List;
import java.util.function.Supplier;

public class SassSQLServerUtils {

    public static String parseSql(String sql, List<SassModifyBaseVisitor.TableInfo> tableInfoList, String fieldName, Supplier<String> valueSupplier) {
        return parseSql(sql, new SassModifySQLServerVisitor(tableInfoList, fieldName, valueSupplier));
    }

    public static String parseSql(String sql, SassModifySQLServerVisitor sassModifySQLServerVisitor) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, sassModifySQLServerVisitor.getBaseVisitor().getDbType());
        List<SQLStatement> sqlStatementList = sqlStatementParser.parseStatementList();
        sqlStatementList.forEach(c -> c.accept(sassModifySQLServerVisitor));
        if (sassModifySQLServerVisitor.getBaseVisitor().hasModify()) {
            StringBuilder sbSql = new StringBuilder();
            sqlStatementList.forEach(c -> sbSql.append('\n').append(c.toString()));
            return sbSql.toString();
        } else {
            return null;
        }
    }
}
