package com.zhuang.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zhuang.model.User4Swagger;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class DataCompareUtilsTest {

    @Test
    public void test() {
        User4Swagger user = new User4Swagger();
        user.setName("zwb");
        user.setAge(18);
        user.setHeight(new BigDecimal("1.72"));
        user.setBirthday(new Date());
        User4Swagger wife = new User4Swagger();
        wife.setName("lxc");
        wife.setAge(17);
        wife.setHeight(new BigDecimal("1.60"));
        user.setWife(wife);
        User4Swagger son1 = new User4Swagger();
        son1.setName("zxc");
        son1.setAge(10);
        son1.setHeight(new BigDecimal("1.2"));
        user.getSons().add(son1);
        User4Swagger son2 = new User4Swagger();
        son2.setName("zxy");
        son2.setAge(8);
        son2.setHeight(new BigDecimal("1.0"));
        user.getSons().add(son2);

        User4Swagger user2 = ObjectUtil.clone(user);
        user2.setName("zwb2");
        user2.getWife().setName("lxc2");
        DataCompareUtils.ChangeInfo changeInfo = DataCompareUtils.getChangeInfo(user, user2);
        System.out.println(changeInfo);

    }

}
