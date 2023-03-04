package com.zhuang.hutool;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.Test;
import oshi.hardware.NetworkIF;

import java.util.List;

public class OshiUtilTest {

    @Test
    public void getNetworkIFs() {
        List<NetworkIF> networkIFs = OshiUtil.getNetworkIFs();
        for (NetworkIF item : networkIFs) {
            System.out.println(StrUtil.format("name -> {} | ipv4 -> {} | mac -> {} | displayName -> {}",
                    item.getName(), item.getIPv4addr(), item.getMacaddr(), item.getDisplayName()));
        }
    }

}
