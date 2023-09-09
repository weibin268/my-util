package com.zhuang.util.druid;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;

import java.util.List;

public class ShardingSQLServerUtils {

    public static String parseSql(String sql, List<ShardingModifyBaseVisitor.TableInfo> tableInfoList) {
        return parseSql(sql, new ShardingModifySQLServerVisitor(tableInfoList));
    }

    public static String parseSql(String sql, ShardingModifySQLServerVisitor modifySQLServerVisitor) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, modifySQLServerVisitor.getBaseVisitor().getDbType());
        List<SQLStatement> sqlStatementList = sqlStatementParser.parseStatementList();
        sqlStatementList.forEach(c -> c.accept(modifySQLServerVisitor));
        if (modifySQLServerVisitor.getBaseVisitor().hasModify()) {
            StringBuilder sbSql = new StringBuilder();
            sqlStatementList.forEach(c -> sbSql.append('\n').append(c.toString()));
            return sbSql.toString();
        } else {
            return null;
        }
    }
}
