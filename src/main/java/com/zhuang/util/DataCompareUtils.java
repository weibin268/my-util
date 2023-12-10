package com.zhuang.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class DataCompareUtils {

    public static ChangeInfo getChangeInfo(Object changeBeforeBean, Object changeAfterBean) {
        ChangeInfo result = new ChangeInfo();
        result.setBeforeData(JSON.toJSONString(changeBeforeBean));
        result.setAfterData(JSON.toJSONString(changeAfterBean));
        try {
            List<JSONUtils.DiffInfo> jsonDiffList = JSONUtils.getJsonDiffList(result.getBeforeData(), result.getAfterData());
            Map<String, String> propertyNameMap = SwaggerUtils.getPropertyNameMap(changeBeforeBean);
            Map<String, String> propertyNameMap4After = SwaggerUtils.getPropertyNameMap(changeAfterBean);
            for (Map.Entry<String, String> entry : propertyNameMap4After.entrySet()) {
                if (!propertyNameMap.containsKey(entry.getKey())) {
                    propertyNameMap.put(entry.getKey(), entry.getValue());
                }
            }
            jsonDiffList = jsonDiffList.stream()
                    .filter(item -> Stream.of(".ts", ".id").noneMatch(c -> item.getKey().contains(c)))
                    .collect(Collectors.toList());
            for (JSONUtils.DiffInfo diffInfo : jsonDiffList) {
                if ("A".equals(diffInfo.getType())) {
                    diffInfo.setType("新增");
                } else if ("U".equals(diffInfo.getType())) {
                    diffInfo.setType("修改");
                } else if ("D".equals(diffInfo.getType())) {
                    diffInfo.setType("删除");
                }
                diffInfo.setKey(parseKey(diffInfo.getKey(), propertyNameMap));
            }
            String changeDiffData = jsonDiffList.stream().map(c -> StrUtil.format("【{}】【{}】：{} -> {}", c.getType(), c.getKey(), c.getOldValue(), c.getNewValue())).collect(Collectors.joining("|"));
            result.setDiffData(changeDiffData);
        } catch (Exception e) {
            log.error("DataChangeUtils.getChangeDiffData fail!", e);
        }
        return result;
    }

    private static String parseKey(String key, Map<String, String> propertyNameMap) {
        String result = key;
        String[] keyParts = key.split("\\.");
        List<String> subKeyList = new ArrayList<>();
        String tempSubKey = "";
        for (String keyPart : keyParts) {
            if (StrUtil.isNotEmpty(tempSubKey)) {
                tempSubKey = tempSubKey + ".";
            }
            tempSubKey = tempSubKey + keyPart;
            subKeyList.add(tempSubKey);
        }
        List<String> keyList = new ArrayList<>(propertyNameMap.keySet());
        for (String subKey : subKeyList) {
            String tempKey = keyList.stream().filter(c -> c.equals(subKey)).findFirst().orElse(null);
            if (StrUtil.isNotEmpty(tempKey)) {
                String tempName = propertyNameMap.get(tempKey);
                if (StrUtil.isNotEmpty(tempName)) {
                    String[] subKeyParts = subKey.split("\\.");
                    result = result.replace(subKeyParts[subKeyParts.length - 1], tempName);
                }
            }
        }
        return result;
    }

    @Data
    public static class ChangeInfo {
        private String beforeData;
        private String afterData;
        private String diffData;
    }
}
