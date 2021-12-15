package com.zhuang.util.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportDataResult {

    private Integer totalCount = 0;
    private Integer successCount = 0;
    private Integer failCount = 0;
    private List<FailMsg> failMsgList = new ArrayList<>();

    public void addFailMsg(Integer index, String msg) {
        FailMsg failMsg = new FailMsg();
        failMsg.setIndex(index);        failMsg.setMsg(msg);
        failMsgList.add(failMsg);
    }

    @Data
    public static class FailMsg {
        private Integer index;
        private String msg;
    }
}
