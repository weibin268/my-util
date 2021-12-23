package com.zhuang.util;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

public class SqlUtilsTest {

    @Test
    public void fillSqlParams() {
        String sql = "select * from t1 where name=#{ name } and date=#{date} and age=#{age}";
        Map<String, Object> params = new HashedMap<>();
        params.put("name", "zwb");
        params.put("date", new Date());
        params.put("age", 1234);
        String s = SqlUtils.fillSqlParams(sql, params);
        System.out.println(s);
    }

    @Test
    public void escapeSql() {
        String s = SqlUtils.escapeSql("'update t set b=2'");
        System.out.println(s);
    }

}
