package com.zhuang.hutool;

import cn.hutool.db.Db;
import org.junit.Test;

import java.sql.SQLException;

public class DbTest {

    @Test
    public void test() throws SQLException {
        Db.use().findAll("sys_user").forEach(item->{
            System.out.println(item);
        });
    }

}
